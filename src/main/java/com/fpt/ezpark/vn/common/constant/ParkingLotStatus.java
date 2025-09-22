package com.fpt.ezpark.vn.common.constant;

/**
 * Enum cho trạng thái bãi đỗ xe
 */
public enum ParkingLotStatus {
    ACTIVE("Hoạt động"),
    INACTIVE("Không hoạt động"),
    MAINTENANCE("Bảo trì"),
    FULL("Đầy");

    private final String description;

    ParkingLotStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
