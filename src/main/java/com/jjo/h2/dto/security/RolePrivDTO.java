package com.jjo.h2.dto.security;

import java.util.Set;
import lombok.Data;

@Data
public class RolePrivDTO {

  private Long roleId;

  private Set<Long> privilegeIds;
}
