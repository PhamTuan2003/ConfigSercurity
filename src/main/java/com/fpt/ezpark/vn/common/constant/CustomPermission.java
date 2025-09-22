package com.fpt.ezpark.vn.common.constant;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;

/**
 * Custom permissions cho hệ thống EzPark
 * Mở rộng từ BasePermission để có thêm các quyền cụ thể
 */
public class CustomPermission extends BasePermission {

    // Custom permissions với mask values mở rộng
    public static final Permission VIEW_REPORT = new CustomPermission(32, 'R'); // 0x20
    public static final Permission MANAGE_PAYMENTS = new CustomPermission(64, 'P'); // 0x40
    public static final Permission REFUND = new CustomPermission(128, 'F'); // 0x80
    public static final Permission MANAGE_STAFF = new CustomPermission(256, 'S'); // 0x100
    public static final Permission MANAGE_PROMO = new CustomPermission(512, 'M'); // 0x200
    public static final Permission CANCEL_RESERVATION = new CustomPermission(1024, 'C'); // 0x400
    public static final Permission TOPUP_WALLET = new CustomPermission(2048, 'T'); // 0x800
    public static final Permission WITHDRAW_WALLET = new CustomPermission(4096, 'W'); // 0x1000

    protected CustomPermission(int mask, char code) {
        super(mask, code);
    }
}
