package com.fpt.ezpark.vn.common.constant;

/**
 * Enum cho loại slot đỗ xe
 */
public enum SlotType {
    REGULAR("Thường"),
    PREMIUM("Cao cấp"),
    HANDICAP("Dành cho người khuyết tật"),
    MOTORCYCLE("Xe máy"),
    ELECTRIC_VEHICLE("Xe điện");

    private final String description;

    SlotType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
