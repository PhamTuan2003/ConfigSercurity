package com.fpt.ezpark.vn.common.constant;

/**
 * Enum cho trạng thái nhân viên
 */
public enum EmployeeStatus {
    ACTIVE("Hoạt động"),
    INACTIVE("Không hoạt động"),
    TERMINATED("Đã nghỉ việc"),
    SUSPENDED("Tạm đình chỉ");

    private final String description;

    EmployeeStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
