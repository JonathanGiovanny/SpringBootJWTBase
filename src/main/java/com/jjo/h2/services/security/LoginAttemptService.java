package com.jjo.h2.services.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

public interface LoginAttemptService {

  void registerFailedAttempt(WebAuthenticationDetails details);

  void registerSuccessAttempt(WebAuthenticationDetails details);

  boolean isBlocked(WebAuthenticationDetails details);

  void removeExpiredAttempts();

}
