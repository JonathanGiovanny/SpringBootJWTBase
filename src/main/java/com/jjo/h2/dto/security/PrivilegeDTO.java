package com.jjo.h2.dto.security;

import lombok.Data;

@Data
public class PrivilegeDTO {

  private Long id;

  /**
   * Represents the value that will be used for the preauthorize validation
   */
  private String name;

  private String description;
}
