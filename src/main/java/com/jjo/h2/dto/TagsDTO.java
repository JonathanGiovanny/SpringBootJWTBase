package com.jjo.h2.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TagsDTO {

  private Long id;

  @NotNull
  private String name;
}
