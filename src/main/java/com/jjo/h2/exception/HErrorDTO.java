package com.jjo.h2.exception;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HErrorDTO {

  private Long id;
  private String userMessage;
  private String techMessage;
  private LocalDateTime timestamp;
  private String field;
  private String path;
}
