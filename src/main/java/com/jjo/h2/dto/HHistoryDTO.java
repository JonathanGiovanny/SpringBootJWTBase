package com.jjo.h2.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class HHistoryDTO {

  private Long id;

  private HDTO h;

  private LocalDateTime date;
}
