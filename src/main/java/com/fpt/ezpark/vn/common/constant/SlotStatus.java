package com.fpt.ezpark.vn.common.constant;

/**
 * Enum cho trạng thái slot đỗ xe
 */
public enum SlotStatus {
    AVAILABLE("Có sẵn"),
    OCCUPIED("Đã có xe"),
    RESERVED("Đã đặt trước"),
    MAINTENANCE("Bảo trì"),
    OUT_OF_ORDER("Hỏng");

    private final String description;

    SlotStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
