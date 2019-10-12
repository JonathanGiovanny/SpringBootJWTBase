package com.jjo.h2.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import com.jjo.h2.services.security.LoginAttemptService;
import com.jjo.h2.services.security.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

  @Autowired
  private final @NonNull LoginAttemptService loginAttemptService;

  private final @NonNull UserService userService;

  @Override
  public void onApplicationEvent(AuthenticationSuccessEvent event) {
    WebAuthenticationDetails details = (WebAuthenticationDetails) event.getAuthentication().getDetails();
    loginAttemptService.registerSuccessAttempt(details);

    final String username = event.getAuthentication().getName();
    userService.userAttemptReset(username);
  }
}
