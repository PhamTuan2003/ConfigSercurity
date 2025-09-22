package com.fpt.ezpark.vn.common.constant;

/**
 * Enum cho loại slot đỗ xe
 */
public enum SlotType {
    REGULAR("Thường"),
    PREMIUM("Cao cấp");

    private final String description;

    SlotType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
