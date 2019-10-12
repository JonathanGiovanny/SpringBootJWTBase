package com.jjo.h2.controller.security;

import static com.jjo.h2.config.security.SecurityConstants.ADMIN;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jjo.h2.config.security.SecurityConstants;
import com.jjo.h2.dto.security.RoleDTO;
import com.jjo.h2.mapper.security.RoleMapper;
import com.jjo.h2.services.security.RolesService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@PreAuthorize(ADMIN)
@RequestMapping(SecurityConstants.SECURITY_PATH + "/roles")
public class RolesController {

  private final @NonNull RolesService rolesService;

  private final @NonNull RoleMapper mapper;

  @GetMapping
  public ResponseEntity<List<RoleDTO>> getRoles() {
    return ResponseEntity.ok(mapper.entityToDto(rolesService.getRoles()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<RoleDTO> getRoles(@PathVariable Long id) {
    return ResponseEntity.ok(mapper.entityToDto(rolesService.getRoleById(id)));
  }

  @PostMapping
  public ResponseEntity<Void> saveRole(@RequestBody RoleDTO role) {
    return ResponseEntity.created(URI.create(rolesService.createRole(mapper.dtoToEntity(role)).toString())).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<RoleDTO> updateRole(@PathVariable Long id, @RequestBody RoleDTO role) {
    return ResponseEntity.ok(mapper.entityToDto(rolesService.updateRole(id, mapper.dtoToEntity(role))));
  }

  @PatchMapping("/{id}/privileges")
  public ResponseEntity<RoleDTO> updateRolePriv(@PathVariable Long id, @RequestBody RoleDTO role) {
    return ResponseEntity.ok(mapper.entityToDto(rolesService.updateRolePrivileges(id, mapper.dtoToEntity(role))));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> updateRole(@PathVariable Long id) {
    rolesService.deleteRole(id);
    return ResponseEntity.noContent().build();
  }
}
