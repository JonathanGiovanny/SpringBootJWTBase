package com.jjo.h2.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import com.jjo.h2.services.security.LoginAttemptService;
import com.jjo.h2.services.security.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AuthenticationFailureEventListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

  private final @NonNull UserService userService;

  @Autowired
  private final @NonNull LoginAttemptService loginAttemptService;

  @Override
  public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
    WebAuthenticationDetails details = (WebAuthenticationDetails) event.getAuthentication().getDetails();
    loginAttemptService.registerFailedAttempt(details);

    final String username = event.getAuthentication().getName();
    if (!userService.availableUsernameOrEmail(username, null)) {
      userService.incrementUserAttempt(username);
    }
  }
}
