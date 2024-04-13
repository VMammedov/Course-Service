package com.company.courseservice.config;

import com.company.courseservice.domain.UserAuthority;
import com.company.courseservice.domain.UserRole;
import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final UserRoleRepository userRoleRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        String targetType = null;
        if (authentication == null || permission == null) {
            return false;
        }

        if(targetDomainObject != null)
            targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();

        return hasPrivilege(authentication, targetType, permission.toString().toUpperCase());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if (authentication == null || permission == null) {
            return false;
        }

        return hasPrivilege(authentication, targetType.toUpperCase(), permission.toString().toUpperCase());
    }

    private boolean hasPrivilege(Authentication authentication, String targetType, String permission) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority userAuthority : authorities) {
            UserRole userRole = userRoleRepository.findByName(userAuthority.getAuthority())
                    .orElseThrow(() -> new DataNotFoundException("Role not found!"));

            if(userRole == null || userRole.getAuthorities().isEmpty())
                return false;

            for (UserAuthority authority : userRole.getAuthorities()) {
                if (authority.getName().equals(permission))
                    return true;
            }
        }
        return false;
    }
}
