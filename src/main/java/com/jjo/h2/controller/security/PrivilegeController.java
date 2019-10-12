package com.jjo.h2.controller.security;

import static com.jjo.h2.config.security.SecurityConstants.ADMIN;
import java.net.URI;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jjo.h2.config.security.SecurityConstants;
import com.jjo.h2.dto.security.PrivilegeDTO;
import com.jjo.h2.mapper.security.PrivilegeMapper;
import com.jjo.h2.services.security.PrivilegeService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@PreAuthorize(ADMIN)
@RequestMapping(SecurityConstants.SECURITY_PATH + "/privileges")
public class PrivilegeController {

  private final @NonNull PrivilegeService privilegeService;

  private final @NonNull PrivilegeMapper mapper;

  @GetMapping
  public ResponseEntity<List<PrivilegeDTO>> getRoles(Pageable pageable) {
    return ResponseEntity.ok(mapper.entityToDto(privilegeService.findAll(pageable)));
  }

  @PostMapping
  public ResponseEntity<Void> saveRole(@RequestBody PrivilegeDTO privilege) {
    return ResponseEntity.created(URI.create(privilegeService.savePrivilege(mapper.dtoToEntity(privilege)).getId().toString())).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<PrivilegeDTO> updateRole(@PathVariable Long id, @RequestBody PrivilegeDTO privilege) {
    return ResponseEntity.ok(mapper.entityToDto(privilegeService.updatePrivilege(id, mapper.dtoToEntity(privilege))));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
    privilegeService.deletePrivilege(id);
    return ResponseEntity.noContent().build();
  }
}
