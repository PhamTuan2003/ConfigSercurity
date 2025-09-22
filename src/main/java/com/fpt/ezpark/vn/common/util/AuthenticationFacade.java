package com.fpt.ezpark.vn.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Utility class để lấy thông tin authentication hiện tại
 */
@Component
public class AuthenticationFacade {

    /**
     * Lấy Authentication object hiện tại
     */
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Lấy username của user hiện tại
     */
    public String getCurrentUsername() {
        Authentication authentication = getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }

    /**
     * Kiểm tra xem user hiện tại có role cụ thể không
     */
    public boolean hasRole(String role) {
        Authentication authentication = getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals(role));
        }
        return false;
    }

    /**
     * Kiểm tra xem user hiện tại có bất kỳ role nào trong danh sách không
     */
    public boolean hasAnyRole(String... roles) {
        Authentication authentication = getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getAuthorities().stream()
                    .anyMatch(authority -> {
                        String authorityName = authority.getAuthority();
                        for (String role : roles) {
                            if (authorityName.equals(role)) {
                                return true;
                            }
                        }
                        return false;
                    });
        }
        return false;
    }

    /**
     * Kiểm tra xem user hiện tại đã được authenticate chưa
     */
    public boolean isAuthenticated() {
        Authentication authentication = getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }
}
