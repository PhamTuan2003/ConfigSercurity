package com.fpt.ezpark.vn.model.entity;

import java.math.BigDecimal;

import com.fpt.ezpark.vn.common.constant.ParkingLotStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parking_lot")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ParkingLot extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "available_slots")
    private Integer availableSlots;

    @Column(name = "hourly_rate", nullable = false)
    private BigDecimal hourlyRate;

    @Column(name = "daily_rate")
    private BigDecimal dailyRate;

    @Column(name = "monthly_rate")
    private BigDecimal monthlyRate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ParkingLotStatus status;

    @Column(name = "policies")
    private String policies; // JSON string for parking policies

    @Column(name = "deposit_required")
    private BigDecimal depositRequired;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Override
    protected void onCreate() {
        super.onCreate();
        if (availableSlots == null) {
            availableSlots = capacity;
        }
    }
}