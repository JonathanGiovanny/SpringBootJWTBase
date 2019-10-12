package com.jjo.h2.dto.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDTO {

  private String username;

  private String password;

  @JsonCreator
  public LoginDTO(@JsonProperty(value = "username", required = true) String username,
      @JsonProperty(value = "password", required = true) String password) {
    super();
    this.username = username;
    this.password = password;
  }
}
