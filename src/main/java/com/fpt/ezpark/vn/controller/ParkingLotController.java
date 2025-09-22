package com.fpt.ezpark.vn.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.ezpark.vn.common.constant.ParkingLotStatus;
import com.fpt.ezpark.vn.common.util.AuthenticationFacade;
import com.fpt.ezpark.vn.model.DTO.request.ParkingLotRequestDTO;
import com.fpt.ezpark.vn.model.entity.ParkingLot;
import com.fpt.ezpark.vn.service.ParkingLotService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/parking-lots")
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotService parkingLotService;
    private final AuthenticationFacade authenticationFacade;

    /**
     * Tạo bãi đỗ xe mới - chỉ Owner có thể tạo
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ParkingLot> createParkingLot(@Valid @RequestBody ParkingLotRequestDTO request) {
        String currentUsername = authenticationFacade.getCurrentUsername();
        ParkingLot parkingLot = parkingLotService.createParkingLot(request, currentUsername);
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingLot);
    }

    /**
     * Lấy thông tin bãi đỗ xe - kiểm tra quyền READ
     */
    @GetMapping("/{id}")
    @PostAuthorize("hasPermission(returnObject, 'READ') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ParkingLot> getParkingLot(@PathVariable Long id) {
        ParkingLot parkingLot = parkingLotService.findById(id);
        return ResponseEntity.ok(parkingLot);
    }

    /**
     * Lấy danh sách bãi đỗ xe - lọc theo quyền READ
     */
    @GetMapping
    @PostFilter("hasPermission(filterObject, 'READ') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ParkingLot>> getAllParkingLots() {
        List<ParkingLot> parkingLots = parkingLotService.findAll();
        return ResponseEntity.ok(parkingLots);
    }

    /**
     * Lấy danh sách bãi đỗ xe của owner
     */
    @GetMapping("/my-parking-lots")
    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ParkingLot>> getMyParkingLots() {
        String currentUsername = authenticationFacade.getCurrentUsername();
        List<ParkingLot> parkingLots = parkingLotService.findByOwner(currentUsername);
        return ResponseEntity.ok(parkingLots);
    }

    /**
     * Cập nhật bãi đỗ xe - kiểm tra quyền WRITE
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'com.fpt.ezpark.vn.model.entity.ParkingLot', 'WRITE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ParkingLot> updateParkingLot(@PathVariable Long id,
            @Valid @RequestBody ParkingLotRequestDTO request) {
        ParkingLot parkingLot = parkingLotService.updateParkingLot(id, request);
        return ResponseEntity.ok(parkingLot);
    }

    /**
     * Xóa bãi đỗ xe - kiểm tra quyền ADMINISTRATION
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'com.fpt.ezpark.vn.model.entity.ParkingLot', 'ADMINISTRATION') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteParkingLot(@PathVariable Long id) {
        parkingLotService.deleteParkingLot(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Cập nhật trạng thái bãi đỗ xe - kiểm tra quyền WRITE
     */
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasPermission(#id, 'com.fpt.ezpark.vn.model.entity.ParkingLot', 'WRITE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ParkingLot> updateStatus(@PathVariable Long id,
            @RequestParam ParkingLotStatus status) {
        ParkingLot parkingLot = parkingLotService.updateStatus(id, status);
        return ResponseEntity.ok(parkingLot);
    }

    /**
     * Lấy danh sách bãi đỗ xe có sẵn - public endpoint
     */
    @GetMapping("/available")
    public ResponseEntity<List<ParkingLot>> getAvailableParkingLots() {
        List<ParkingLot> parkingLots = parkingLotService.findAvailableParkingLots();
        return ResponseEntity.ok(parkingLots);
    }
}
