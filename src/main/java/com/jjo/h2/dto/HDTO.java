package com.jjo.h2.dto;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import lombok.Data;

@Data
public class HDTO {

  private Long id;

  private String name;

  @NotNull
  private String url;

  private String cover;

  @Null
  private Long clicks;

  private Double score;

  @NotNull
  private HTypeDTO type;

  private List<TagsDTO> tags;

  @Null
  private LocalDateTime modifiedDate;
}
