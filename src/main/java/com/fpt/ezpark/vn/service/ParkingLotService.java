package com.fpt.ezpark.vn.service;

import java.util.List;

import com.fpt.ezpark.vn.common.constant.ParkingLotStatus;
import com.fpt.ezpark.vn.model.DTO.request.ParkingLotRequestDTO;
import com.fpt.ezpark.vn.model.entity.ParkingLot;

public interface ParkingLotService {

    ParkingLot createParkingLot(ParkingLotRequestDTO request, String ownerUsername);

    ParkingLot findById(Long id);

    List<ParkingLot> findAll();

    List<ParkingLot> findByOwner(String ownerUsername);

    ParkingLot updateParkingLot(Long id, ParkingLotRequestDTO request);

    void deleteParkingLot(Long id);

    List<ParkingLot> findAvailableParkingLots();

    ParkingLot updateStatus(Long id, ParkingLotStatus status);
}
