package com.jjo.h2.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class HException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -343694406217060815L;

  private String userMessage;
  private String techMessage;
  private String field;

  /**
   * Send messages and Exception
   * 
   * @param message
   * @param e
   */
  public HException(Errors error, Object... args) {
    super(error.getMessage());
    this.userMessage = String.format(error.getCode(), args);
    this.techMessage = String.format(error.getMessage(), args);
  }

/**
   * Send messages, field and Exception
   * 
   * @param message
   * @param e
   */
  public HException(Errors error, String field, Object... args) {
    super(error.getMessage());
    this.userMessage = String.format(error.getCode(), args);
    this.techMessage = String.format(error.getMessage(), args);
    this.field = field;
  }

  /**
   * Send messages and Exception
   * 
   * @param message
   * @param e
   */
  public HException(Errors error, Exception e, Object... args) {
    super(error.getMessage(), e);
    this.userMessage = String.format(error.getCode());
    this.techMessage = String.format(error.getMessage());
  }

  /**
   * Send messages, field and Exception
   * 
   * @param message
   * @param e
   */
  public HException(Errors error, String field, Exception e, Object... args) {
    super(error.getMessage(), e);
    this.userMessage = String.format(error.getCode());
    this.techMessage = String.format(error.getMessage());
    this.field = field;
  }
}
