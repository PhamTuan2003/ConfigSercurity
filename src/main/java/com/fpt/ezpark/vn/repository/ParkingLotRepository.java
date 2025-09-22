package com.fpt.ezpark.vn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.ezpark.vn.common.constant.ParkingLotStatus;
import com.fpt.ezpark.vn.model.entity.ParkingLot;
import com.fpt.ezpark.vn.model.entity.User;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {

    List<ParkingLot> findByOwner(User owner);

    List<ParkingLot> findByStatus(ParkingLotStatus status);

    List<ParkingLot> findByOwnerAndStatus(User owner, ParkingLotStatus status);
}
