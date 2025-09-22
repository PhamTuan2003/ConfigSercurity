package com.fpt.ezpark.vn.configuration.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.fpt.ezpark.vn.model.entity.IEntity;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class PermissionService {

    @Autowired
    private MutableAclService aclService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    public void addPermissionForUser(IEntity targetObj, Permission permission, String username) {
        final Sid sid = new PrincipalSid(username);
        addPermissionForSid(targetObj, permission, sid);
        log.info("Grant {} permission to principal {} on Object {}",
                permission, username, targetObj);
    }

    /**
     * Gán quyền cho role (ví dụ ROLE_ADMIN, ROLE_EMPLOYEE).
     */
    public void addPermissionForRole(IEntity targetObj, Permission permission, String role) {
        final Sid sid = new GrantedAuthoritySid(role);
        addPermissionForSid(targetObj, permission, sid);
        log.info("Grant {} permission to role {} on Object {}",
                permission, role, targetObj);
    }

    /**
     * Gán quyền cho user cụ thể với ObjectIdentity.
     */
    public void addPermissionForUser(ObjectIdentity oid, Permission permission, String username) {
        final Sid sid = new PrincipalSid(username);
        addPermissionForSid(oid, permission, sid);
        log.info("Grant {} permission to principal {} on ObjectIdentity {}",
                permission, username, oid);
    }

    /**
     * Gán quyền cho role với ObjectIdentity.
     */
    public void addPermissionForRole(ObjectIdentity oid, Permission permission, String role) {
        final Sid sid = new GrantedAuthoritySid(role);
        addPermissionForSid(oid, permission, sid);
        log.info("Grant {} permission to role {} on ObjectIdentity {}",
                permission, role, oid);
    }

    public void removeAllPermissionForUser(IEntity targetObj, String username) {
        final Sid sid = new PrincipalSid(username);
        deleteAllPermissionForSid(targetObj, sid);
        log.info("Remove all permissions to principal {} on Object {}",
                username, targetObj);
    }

    public void removeAcl(IEntity targetObj) {
        deleteAcl(targetObj);
        log.info("Remove ACL on Object {}", targetObj);
    }

    /**
     * Xóa quyền cụ thể cho user.
     */
    public void removePermissionForUser(IEntity targetObj, Permission permission, String username) {
        final Sid sid = new PrincipalSid(username);
        removePermissionForSid(targetObj, permission, sid);
        log.info("Remove {} permission from principal {} on Object {}",
                permission, username, targetObj);
    }

    /**
     * Xóa quyền cụ thể cho role.
     */
    public void removePermissionForRole(IEntity targetObj, Permission permission, String role) {
        final Sid sid = new GrantedAuthoritySid(role);
        removePermissionForSid(targetObj, permission, sid);
        log.info("Remove {} permission from role {} on Object {}",
                permission, role, targetObj);
    }

    /**
     * Kiểm tra xem user có quyền trên object hay không.
     */
    public boolean hasPermission(IEntity targetObj, Permission permission, String username) {
        try {
            ObjectIdentity oid = new ObjectIdentityImpl(targetObj.getClass(), targetObj.getId());
            return hasPermission(oid, permission, username);
        } catch (Exception e) {
            log.error("Error checking permission for user {} on object {}", username, targetObj, e);
            return false;
        }
    }

    /**
     * Kiểm tra xem user có quyền trên ObjectIdentity hay không.
     */
    public boolean hasPermission(ObjectIdentity oid, Permission permission, String username) {
        try {
            MutableAcl acl = (MutableAcl) aclService.readAclById(oid);
            Sid sid = new PrincipalSid(username);
            return acl.isGranted(List.of(permission), List.of(sid), false);
        } catch (NotFoundException ex) {
            log.debug("ACL not found for object identity: {}", oid);
            return false;
        } catch (Exception e) {
            log.error("Error checking permission for user {} on object identity {}", username, oid, e);
            return false;
        }
    }

    /**
     * Kiểm tra xem role có quyền trên object hay không.
     */
    public boolean hasPermissionForRole(IEntity targetObj, Permission permission, String role) {
        try {
            ObjectIdentity oid = new ObjectIdentityImpl(targetObj.getClass(), targetObj.getId());
            MutableAcl acl = (MutableAcl) aclService.readAclById(oid);
            Sid sid = new GrantedAuthoritySid(role);
            return acl.isGranted(List.of(permission), List.of(sid), false);
        } catch (NotFoundException ex) {
            log.debug("ACL not found for object: {}", targetObj);
            return false;
        } catch (Exception e) {
            log.error("Error checking permission for role {} on object {}", role, targetObj, e);
            return false;
        }
    }

    private void addPermissionForSid(IEntity targetObj, Permission permission, Sid sid) {
        final ObjectIdentity oi = new ObjectIdentityImpl(targetObj.getClass(), targetObj.getId());
        addPermissionForSid(oi, permission, sid);
    }

    private void addPermissionForSid(ObjectIdentity oid, Permission permission, Sid sid) {
        final TransactionTemplate tt = new TransactionTemplate(transactionManager);

        tt.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                MutableAcl acl = null;
                try {
                    acl = (MutableAcl) aclService.readAclById(oid);
                } catch (final NotFoundException nfe) {
                    acl = aclService.createAcl(oid);
                }

                acl.insertAce(acl.getEntries().size(), permission, sid, true);
                aclService.updateAcl(acl);
            }
        });
    }

    private void removePermissionForSid(IEntity targetObj, Permission permission, Sid sid) {
        final TransactionTemplate tt = new TransactionTemplate(transactionManager);

        tt.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                final ObjectIdentity oi = new ObjectIdentityImpl(targetObj.getClass(), targetObj.getId());
                try {
                    MutableAcl acl = (MutableAcl) aclService.readAclById(oi);
                    List<AccessControlEntry> aclEntries = acl.getEntries();
                    for (int i = aclEntries.size() - 1; i >= 0; i--) {
                        AccessControlEntry ace = aclEntries.get(i);
                        if (ace.getSid().equals(sid) && ace.getPermission().equals(permission)) {
                            acl.deleteAce(i);
                            break;
                        }
                    }
                    aclService.updateAcl(acl);
                } catch (NotFoundException ignore) {
                    // ACL not found, nothing to remove
                }
            }
        });
    }

    private void deleteAllPermissionForSid(IEntity targetObj, Sid sid) {
        final TransactionTemplate tt = new TransactionTemplate(transactionManager);

        tt.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                final ObjectIdentity oi = new ObjectIdentityImpl(targetObj.getClass(), targetObj.getId());
                try {
                    MutableAcl acl = (MutableAcl) aclService.readAclById(oi);
                    List<AccessControlEntry> aclEntries = acl.getEntries();
                    for (int i = aclEntries.size() - 1; i >= 0; i--) {
                        AccessControlEntry ace = aclEntries.get(i);
                        if (ace.getSid().equals(sid)) {
                            acl.deleteAce(i);
                        }
                    }
                    if (acl.getEntries().isEmpty()) {
                        aclService.deleteAcl(oi, true);
                    }
                    aclService.updateAcl(acl);
                } catch (NotFoundException ignore) {
                }
            }
        });
    }

    private void deleteAcl(IEntity targetObj) {
        final TransactionTemplate tt = new TransactionTemplate(transactionManager);

        tt.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                final ObjectIdentity oi = new ObjectIdentityImpl(targetObj.getClass(), targetObj.getId());
                try {
                    MutableAcl acl = (MutableAcl) aclService.readAclById(oi);
                    aclService.deleteAcl(oi, true);
                } catch (NotFoundException ignore) {
                }
            }
        });
    }

}
