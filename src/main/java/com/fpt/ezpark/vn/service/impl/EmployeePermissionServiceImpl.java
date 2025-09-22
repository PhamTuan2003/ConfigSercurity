package com.fpt.ezpark.vn.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fpt.ezpark.vn.common.constant.CustomPermission;
import com.fpt.ezpark.vn.configuration.security.PermissionService;
import com.fpt.ezpark.vn.model.entity.ParkingLot;
import com.fpt.ezpark.vn.model.entity.User;
import com.fpt.ezpark.vn.service.EmployeePermissionService;
import com.fpt.ezpark.vn.service.ParkingLotService;
import com.fpt.ezpark.vn.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeePermissionServiceImpl implements EmployeePermissionService {

    private final PermissionService permissionService;
    private final ParkingLotService parkingLotService;
    private final UserService userService;

    @Override
    public void assignEmployeeToParkingLot(String employeeUsername, Long parkingLotId, String roleInLot) {
        // Kiểm tra employee tồn tại
        User employee = userService.findUserByEmail(employeeUsername);
        if (employee == null) {
            throw new RuntimeException("Employee not found: " + employeeUsername);
        }

        // Kiểm tra parking lot tồn tại
        parkingLotService.findById(parkingLotId);

        // Gán quyền mặc định dựa trên role
        assignDefaultPermissionsForRole(employeeUsername, parkingLotId, roleInLot);
    }

    @Override
    public void grantPermissionToEmployee(String employeeUsername, Long parkingLotId, Permission permission) {
        ParkingLot parkingLot = parkingLotService.findById(parkingLotId);
        permissionService.addPermissionForUser(parkingLot, permission, employeeUsername);
    }

    @Override
    public void revokePermissionFromEmployee(String employeeUsername, Long parkingLotId, Permission permission) {
        ParkingLot parkingLot = parkingLotService.findById(parkingLotId);
        permissionService.removePermissionForUser(parkingLot, permission, employeeUsername);
    }

    @Override
    public void removeEmployeeFromParkingLot(String employeeUsername, Long parkingLotId) {
        ParkingLot parkingLot = parkingLotService.findById(parkingLotId);
        permissionService.removeAllPermissionForUser(parkingLot, employeeUsername);
    }

    @Override
    public boolean hasEmployeePermission(String employeeUsername, Long parkingLotId, Permission permission) {
        ParkingLot parkingLot = parkingLotService.findById(parkingLotId);
        return permissionService.hasPermission(parkingLot, permission, employeeUsername);
    }

    @Override
    public void assignDefaultPermissionsForRole(String employeeUsername, Long parkingLotId, String roleInLot) {
        ParkingLot parkingLot = parkingLotService.findById(parkingLotId);

        List<Permission> permissions = getDefaultPermissionsForRole(roleInLot);

        for (Permission permission : permissions) {
            permissionService.addPermissionForUser(parkingLot, permission, employeeUsername);
        }
    }

    /**
     * Lấy danh sách quyền mặc định cho từng role
     */
    private List<Permission> getDefaultPermissionsForRole(String roleInLot) {
        switch (roleInLot.toUpperCase()) {
            case "MANAGER":
                return Arrays.asList(
                        BasePermission.READ,
                        BasePermission.WRITE,
                        BasePermission.CREATE,
                        BasePermission.DELETE,
                        CustomPermission.VIEW_REPORT,
                        CustomPermission.MANAGE_STAFF,
                        CustomPermission.MANAGE_PAYMENTS);
            case "STAFF":
                return Arrays.asList(
                        BasePermission.READ,
                        BasePermission.WRITE,
                        BasePermission.CREATE,
                        CustomPermission.CANCEL_RESERVATION);
            case "SECURITY":
                return Arrays.asList(
                        BasePermission.READ,
                        CustomPermission.CANCEL_RESERVATION);
            case "CASHIER":
                return Arrays.asList(
                        BasePermission.READ,
                        BasePermission.WRITE,
                        CustomPermission.MANAGE_PAYMENTS,
                        CustomPermission.REFUND);
            default:
                // Quyền cơ bản cho employee
                return Arrays.asList(
                        BasePermission.READ,
                        BasePermission.WRITE);
        }
    }
}
