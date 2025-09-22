package com.fpt.ezpark.vn.service.impl;

import java.util.List;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fpt.ezpark.vn.common.constant.ParkingLotStatus;
import com.fpt.ezpark.vn.common.constant.SystemRole;
import com.fpt.ezpark.vn.configuration.security.PermissionService;
import com.fpt.ezpark.vn.model.DTO.request.ParkingLotRequestDTO;
import com.fpt.ezpark.vn.model.entity.ParkingLot;
import com.fpt.ezpark.vn.model.entity.User;
import com.fpt.ezpark.vn.repository.ParkingLotRepository;
import com.fpt.ezpark.vn.service.ParkingLotService;
import com.fpt.ezpark.vn.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ParkingLotServiceImpl implements ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;
    private final UserService userService;
    private final PermissionService permissionService;

    @Override
    public ParkingLot createParkingLot(ParkingLotRequestDTO request, String ownerUsername) {
        User owner = userService.findUserByEmail(ownerUsername);
        if (owner == null) {
            throw new RuntimeException("Owner not found: " + ownerUsername);
        }

        ParkingLot parkingLot = ParkingLot.builder()
                .name(request.getName())
                .address(request.getAddress())
                .description(request.getDescription())
                .capacity(request.getCapacity())
                .hourlyRate(request.getHourlyRate())
                .dailyRate(request.getDailyRate())
                .monthlyRate(request.getMonthlyRate())
                .policies(request.getPolicies())
                .depositRequired(request.getDepositRequired())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .ownerId(owner.getId())
                .status(ParkingLotStatus.ACTIVE)
                .build();

        ParkingLot savedParkingLot = parkingLotRepository.save(parkingLot);

        // Gán quyền ADMINISTRATION cho owner
        permissionService.addPermissionForUser(savedParkingLot, BasePermission.ADMINISTRATION, ownerUsername);

        // Gán quyền cho ROLE_ADMIN (nếu cần)
        permissionService.addPermissionForRole(savedParkingLot, BasePermission.ADMINISTRATION, SystemRole.ROLE_ADMIN);

        return savedParkingLot;
    }

    @Override
    @Transactional(readOnly = true)
    public ParkingLot findById(Long id) {
        return parkingLotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ParkingLot not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParkingLot> findAll() {
        return parkingLotRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParkingLot> findByOwner(String ownerUsername) {
        User owner = userService.findUserByEmail(ownerUsername);
        if (owner == null) {
            throw new RuntimeException("Owner not found: " + ownerUsername);
        }
        return parkingLotRepository.findByOwnerId(owner.getId());
    }

    @Override
    public ParkingLot updateParkingLot(Long id, ParkingLotRequestDTO request) {
        ParkingLot parkingLot = findById(id);

        parkingLot.setName(request.getName());
        parkingLot.setAddress(request.getAddress());
        parkingLot.setDescription(request.getDescription());
        parkingLot.setCapacity(request.getCapacity());
        parkingLot.setHourlyRate(request.getHourlyRate());
        parkingLot.setDailyRate(request.getDailyRate());
        parkingLot.setMonthlyRate(request.getMonthlyRate());
        parkingLot.setPolicies(request.getPolicies());
        parkingLot.setDepositRequired(request.getDepositRequired());
        parkingLot.setLatitude(request.getLatitude());
        parkingLot.setLongitude(request.getLongitude());

        return parkingLotRepository.save(parkingLot);
    }

    @Override
    public void deleteParkingLot(Long id) {
        ParkingLot parkingLot = findById(id);
        parkingLotRepository.delete(parkingLot);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParkingLot> findAvailableParkingLots() {
        return parkingLotRepository.findByStatus(ParkingLotStatus.ACTIVE);
    }

    @Override
    public ParkingLot updateStatus(Long id, ParkingLotStatus status) {
        ParkingLot parkingLot = findById(id);
        parkingLot.setStatus(status);
        return parkingLotRepository.save(parkingLot);
    }
}
