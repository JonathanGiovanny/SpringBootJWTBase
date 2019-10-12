package com.jjo.h2.config.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {

  static final String WWW_AUTHENTICATE = "WWW-Authenticate";

  public static final String SECURITY_PATH = "/security";
  public static final String AUTH_LOGIN_URL = SECURITY_PATH + "/login";
  public static final String TOKEN_PREFIX = "JWT ";
  public static final String TOKEN_HEADER = "Authorization";
  public static final String ALLOW_HEADERS = "Access-Control-Allow-Headers";
  public static final Long PREFLIGHT_AGE = 3600L;

  public static final String MODIFY_H = "hasPermission(#this, 'MODIFY_H')";
  public static final String DELETE_H = "hasPermission(#this, 'DELETE_H')";
  public static final String MODIFY_TAGS = "hasPermission(#this, 'MODIFY_TAGS')";
  public static final String DELETE_TAGS = "hasPermission(#this, 'DELETE_TAGS')";
  public static final String MODIFY_TYPES = "hasPermission(#this, 'MODIFY_TYPES')";
  public static final String DELETE_TYPES = "hasPermission(#this, 'DELETE_TYPES')";

  public static final String ADMIN = "hasRole('ADMIN')";
}
