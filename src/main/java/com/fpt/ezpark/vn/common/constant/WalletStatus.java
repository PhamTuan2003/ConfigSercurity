package com.fpt.ezpark.vn.common.constant;

/**
 * Enum cho trạng thái ví tiền
 */
public enum WalletStatus {
    ACTIVE("Hoạt động"),
    SUSPENDED("Tạm khóa"),
    CLOSED("Đã đóng");

    private final String description;

    WalletStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
