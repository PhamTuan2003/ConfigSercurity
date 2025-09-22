package com.fpt.ezpark.vn.common.constant;

/**
 * Enum cho trạng thái đặt chỗ
 */
public enum ReservationStatus {
    PENDING("Chờ xác nhận"),
    CONFIRMED("Đã xác nhận"),
    ACTIVE("Đang sử dụng"),
    COMPLETED("Hoàn thành"),
    CANCELLED("Đã hủy"),
    EXPIRED("Hết hạn");

    private final String description;

    ReservationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
