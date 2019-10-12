package com.jjo.h2.model.security;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum StatusEnum {

  A("ACTIVE"), B("BLOCKED"), I("INACTIVE");

  private String description;
}
