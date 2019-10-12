package com.jjo.h2.config.security;

import java.io.Serializable;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

public class CustomPermissionEvaluator implements PermissionEvaluator {

  @Override
  public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
    if (authentication == null || !(permission instanceof String)) {
      return false;
    }

    return hasPrivilege(authentication, permission.toString().toUpperCase());
  }

  @Override
  public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
      Object permission) {
    if (authentication == null || !(permission instanceof String)) {
      return false;
    }
    return hasPrivilege(authentication, permission.toString().toUpperCase());
  }

  /**
   * Evaluates the privilege vs the permission to determine if allow or not the access
   * 
   * @param authentication
   * @param permission
   * @return
   */
  private boolean hasPrivilege(Authentication authentication, String permission) {
    return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equalsIgnoreCase(permission));
  }
}
