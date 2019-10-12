package com.jjo.h2.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HTypeDTO {

  private Integer id;

  @NotNull
  private String name;
}
