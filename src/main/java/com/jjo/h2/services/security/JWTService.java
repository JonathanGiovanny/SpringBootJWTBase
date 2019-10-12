package com.jjo.h2.services.security;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import io.jsonwebtoken.Claims;

public interface JWTService {

  /**
   * Generate the token once the user has successfully login
   * 
   * @param authentication
   * @return
   */
  String generateToken(Authentication authentication);

  /**
   * Validates whether the token contains a valid signature
   * 
   * @param token
   * @return
   */
  boolean validateToken(String token, HttpServletRequest request);

  /**
   * Get the claims embedded on the token
   * 
   * @param token
   * @return
   */
  Claims getClaims(String token);
}
