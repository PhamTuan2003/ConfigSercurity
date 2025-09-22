package com.fpt.ezpark.vn.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id")
    private Long ownerId;  // tham chiếu Owner (UserId)

    @Column(name = "user_id")
    private Long userId;   // tham chiếu User (role=EMPLOYEE)
}
