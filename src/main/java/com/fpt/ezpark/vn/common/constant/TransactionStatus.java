package com.fpt.ezpark.vn.common.constant;

/**
 * Enum cho trạng thái giao dịch
 */
public enum TransactionStatus {
    PENDING("Chờ xử lý"),
    COMPLETED("Hoàn thành"),
    FAILED("Thất bại"),
    CANCELLED("Đã hủy");

    private final String description;

    TransactionStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
