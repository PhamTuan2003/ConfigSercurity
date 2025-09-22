package com.fpt.ezpark.vn.common.constant;

/**
 * Enum cho loại giao dịch
 */
public enum TransactionType {
    DEPOSIT("Nạp tiền"),
    WITHDRAWAL("Rút tiền"),
    PAYMENT("Thanh toán"),
    REFUND("Hoàn tiền"),
    BONUS("Thưởng"),
    PENALTY("Phạt");

    private final String description;

    TransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
