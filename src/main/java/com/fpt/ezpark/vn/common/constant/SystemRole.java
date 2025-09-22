package com.fpt.ezpark.vn.common.constant;

/**
 * Các role hệ thống trong EzPark
 */
public class SystemRole {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_OWNER = "ROLE_OWNER";
    public static final String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";
    public static final String ROLE_CUSTOMER = "ROLE_CUSTOMER";

    /**
     * Kiểm tra xem role có phải là admin không
     */
    public static boolean isAdmin(String role) {
        return ROLE_ADMIN.equals(role);
    }

    /**
     * Kiểm tra xem role có phải là owner không
     */
    public static boolean isOwner(String role) {
        return ROLE_OWNER.equals(role);
    }

    /**
     * Kiểm tra xem role có phải là employee không
     */
    public static boolean isEmployee(String role) {
        return ROLE_EMPLOYEE.equals(role);
    }

    /**
     * Kiểm tra xem role có phải là customer không
     */
    public static boolean isCustomer(String role) {
        return ROLE_CUSTOMER.equals(role);
    }

    /**
     * Lấy tất cả roles
     */
    public static String[] getAllRoles() {
        return new String[] { ROLE_ADMIN, ROLE_OWNER, ROLE_EMPLOYEE, ROLE_CUSTOMER };
    }
}
