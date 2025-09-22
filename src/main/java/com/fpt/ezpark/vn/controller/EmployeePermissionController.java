package com.fpt.ezpark.vn.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.ezpark.vn.common.constant.CustomPermission;
import com.fpt.ezpark.vn.service.EmployeePermissionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employee-permissions")
@RequiredArgsConstructor
public class EmployeePermissionController {

    private final EmployeePermissionService employeePermissionService;

    /**
     * Gán employee vào bãi đỗ xe với role cụ thể
     * Chỉ Owner hoặc Admin có thể thực hiện
     */
    @PostMapping("/assign")
    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> assignEmployeeToParkingLot(
            @RequestParam String employeeUsername,
            @RequestParam Long parkingLotId,
            @RequestParam String roleInLot) {

        employeePermissionService.assignEmployeeToParkingLot(employeeUsername, parkingLotId, roleInLot);
        return ResponseEntity.ok("Employee assigned successfully");
    }

    /**
     * Gán quyền cụ thể cho employee
     */
    @PostMapping("/grant")
    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> grantPermissionToEmployee(
            @RequestParam String employeeUsername,
            @RequestParam Long parkingLotId,
            @RequestParam String permission) {

        Permission perm = getPermissionFromString(permission);
        employeePermissionService.grantPermissionToEmployee(employeeUsername, parkingLotId, perm);
        return ResponseEntity.ok("Permission granted successfully");
    }

    /**
     * Thu hồi quyền của employee
     */
    @DeleteMapping("/revoke")
    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> revokePermissionFromEmployee(
            @RequestParam String employeeUsername,
            @RequestParam Long parkingLotId,
            @RequestParam String permission) {

        Permission perm = getPermissionFromString(permission);
        employeePermissionService.revokePermissionFromEmployee(employeeUsername, parkingLotId, perm);
        return ResponseEntity.ok("Permission revoked successfully");
    }

    /**
     * Xóa employee khỏi bãi đỗ xe
     */
    @DeleteMapping("/remove")
    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> removeEmployeeFromParkingLot(
            @RequestParam String employeeUsername,
            @RequestParam Long parkingLotId) {

        employeePermissionService.removeEmployeeFromParkingLot(employeeUsername, parkingLotId);
        return ResponseEntity.ok("Employee removed successfully");
    }

    /**
     * Kiểm tra quyền của employee
     */
    @GetMapping("/check")
    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> checkEmployeePermission(
            @RequestParam String employeeUsername,
            @RequestParam Long parkingLotId,
            @RequestParam String permission) {

        Permission perm = getPermissionFromString(permission);
        boolean hasPermission = employeePermissionService.hasEmployeePermission(employeeUsername, parkingLotId, perm);
        return ResponseEntity.ok(hasPermission);
    }

    /**
     * Chuyển đổi string thành Permission object
     */
    private Permission getPermissionFromString(String permission) {
        switch (permission.toUpperCase()) {
            case "READ":
                return BasePermission.READ;
            case "WRITE":
                return BasePermission.WRITE;
            case "CREATE":
                return BasePermission.CREATE;
            case "DELETE":
                return BasePermission.DELETE;
            case "ADMINISTRATION":
                return BasePermission.ADMINISTRATION;
            case "VIEW_REPORT":
                return CustomPermission.VIEW_REPORT;
            case "MANAGE_PAYMENTS":
                return CustomPermission.MANAGE_PAYMENTS;
            case "REFUND":
                return CustomPermission.REFUND;
            case "MANAGE_STAFF":
                return CustomPermission.MANAGE_STAFF;
            case "MANAGE_PROMO":
                return CustomPermission.MANAGE_PROMO;
            case "CANCEL_RESERVATION":
                return CustomPermission.CANCEL_RESERVATION;
            case "TOPUP_WALLET":
                return CustomPermission.TOPUP_WALLET;
            case "WITHDRAW_WALLET":
                return CustomPermission.WITHDRAW_WALLET;
            default:
                throw new IllegalArgumentException("Unknown permission: " + permission);
        }
    }
}
