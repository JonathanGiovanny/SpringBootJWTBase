package com.jjo.h2.dto.security;

import java.util.Set;
import lombok.Data;

@Data
public class RoleDTO {

  private Long id;

  private String name;

  private Set<PrivilegeDTO> privileges;
}
