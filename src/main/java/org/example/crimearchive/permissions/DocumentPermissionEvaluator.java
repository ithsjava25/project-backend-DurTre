package org.example.crimearchive.permissions;

import org.springframework.orm.jpa.persistenceunit.PersistenceManagedTypes;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;

@Component
public class DocumentPermissionEvaluator implements PermissionEvaluator {

    private final PersistenceManagedTypes persistenceManagedTypes;

    public DocumentPermissionEvaluator(PersistenceManagedTypes persistenceManagedTypes) {
        this.persistenceManagedTypes = persistenceManagedTypes;
    }

    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
        if((auth == null) || (targetDomainObject == null) || !(permission instanceof String))
            return false;
        String targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();
        //hasPrivilege(auth, targetDomainObject, permission.toString().toUpperCase());
        return true;
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
        if ((auth == null) || (targetId == null) || (targetType == null) || !(permission instanceof String))
            return false;

        //Kolla om användaren har tillåtlset att se dokumentet.
        return checkDatabaseForAccess(auth.getName(), (UUID) targetId, permission.toString());
    }

    private boolean checkDatabaseForAccess(String username, UUID docId, String permission){
        //anropa
        return false;
    }
}
