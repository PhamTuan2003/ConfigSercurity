package com.fpt.ezpark.vn.model.entity;

import java.time.LocalDateTime;

import com.fpt.ezpark.vn.common.constant.EmployeeStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "employee_record")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmployeeRecord extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId; // FK -> User.id

    @Column(name = "parking_lot_id", nullable = false)
    private Long parkingLotId; // FK -> ParkingLot.id

    @Column(name = "role_in_lot", nullable = false)
    private String roleInLot; // MANAGER, STAFF...

    @Column(name = "shift")
    private String shift;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "hire_date")
    private LocalDateTime hireDate;

    @Column(name = "termination_date")
    private LocalDateTime terminationDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;
}
