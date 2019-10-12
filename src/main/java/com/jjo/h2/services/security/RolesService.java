package com.jjo.h2.services.security;

import java.util.List;
import com.jjo.h2.model.security.Role;

public interface RolesService {

  Role getRoleById(Long id);

  Role getRoleByName(String name);

  List<Role> getRoles();

  Role createRole(Role role);

  Role updateRole(Long id, Role role);

  /**
   * Update the list of privileges for a Role
   * 
   * @param id
   * @param role
   * @return
   */
  Role updateRolePrivileges(Long id, Role role);

  void deleteRole(Long id);
}
