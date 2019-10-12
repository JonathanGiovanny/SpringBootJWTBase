package com.jjo.h2.services.security;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jjo.h2.config.DatasourceNeo4j;
import com.jjo.h2.model.security.Role;
import com.jjo.h2.repositories.security.RoleRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(value = DatasourceNeo4j.TRANSACTION_MANAGER)
public class RolesServiceImpl implements RolesService {

  private final @NonNull RoleRepository roleRepo;

  @Override
  public Role getRoleById(Long id) {
    return roleRepo.findById(id).orElseThrow();
  }

  @Override
  public Role getRoleByName(String name) {
    return roleRepo.findByName(name);
  }

  @Override
  public List<Role> getRoles() {
    return StreamSupport.stream(roleRepo.findAll().spliterator(), false).collect(Collectors.toList());
  }

  @Override
  public Role createRole(Role role) {
    return roleRepo.save(role);
  }

  @Override
  public Role updateRole(Long id, Role role) {
    Role entity = roleRepo.findById(id).orElseThrow();
    entity.setName(role.getName());
    return roleRepo.save(entity);
  }

  @Override
  public Role updateRolePrivileges(Long id, Role role) {
    Role entity = roleRepo.findById(id).orElseThrow();
    entity.setPrivileges(role.getPrivileges());
    return roleRepo.save(entity);
  }

  @Override
  public void deleteRole(Long id) {
    roleRepo.deleteById(id);
  }
}
