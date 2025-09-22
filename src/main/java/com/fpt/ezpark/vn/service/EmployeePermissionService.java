package com.fpt.ezpark.vn.service;

import org.springframework.security.acls.model.Permission;

/**
 * Service để quản lý quyền cho employees trên các bãi đỗ xe cụ thể
 */
public interface EmployeePermissionService {

    /**
     * Gán quyền cho employee trên bãi đỗ xe cụ thể
     */
    void assignEmployeeToParkingLot(String employeeUsername, Long parkingLotId, String roleInLot);

    /**
     * Gán quyền cụ thể cho employee
     */
    void grantPermissionToEmployee(String employeeUsername, Long parkingLotId, Permission permission);

    /**
     * Thu hồi quyền của employee
     */
    void revokePermissionFromEmployee(String employeeUsername, Long parkingLotId, Permission permission);

    /**
     * Thu hồi tất cả quyền của employee trên bãi đỗ xe
     */
    void removeEmployeeFromParkingLot(String employeeUsername, Long parkingLotId);

    /**
     * Kiểm tra xem employee có quyền trên bãi đỗ xe không
     */
    boolean hasEmployeePermission(String employeeUsername, Long parkingLotId, Permission permission);

    /**
     * Gán quyền mặc định cho employee dựa trên role
     */
    void assignDefaultPermissionsForRole(String employeeUsername, Long parkingLotId, String roleInLot);
}
