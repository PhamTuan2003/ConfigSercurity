package com.fpt.ezpark.vn.configuration.security;

import com.fpt.ezpark.vn.model.entity.User;
import com.fpt.ezpark.vn.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;

@Service
@Transactional
@RequiredArgsConstructor
public class LocalUserDetailsService implements UserDetailsService {

    private static final String ROLE_USER = "ROLE_USER";

    private final UserService userService;

    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                getAuthorities(ROLE_USER));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }
}
