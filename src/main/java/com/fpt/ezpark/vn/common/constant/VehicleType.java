package com.fpt.ezpark.vn.common.constant;

/**
 * Enum cho loại xe
 */
public enum VehicleType {
    CAR("Ô tô"),
    MOTORCYCLE("Xe máy"),
    TRUCK("Xe tải"),
    BUS("Xe buýt"),
    ELECTRIC_VEHICLE("Xe điện");

    private final String description;

    VehicleType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
