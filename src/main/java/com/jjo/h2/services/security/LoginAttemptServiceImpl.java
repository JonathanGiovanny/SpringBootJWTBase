package com.jjo.h2.services.security;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import lombok.Data;

@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {

  @Value("${h.config.ip.loginAttempts}")
  private int loginAttempts;

  @Value("${h.config.ip.minutesBanned}")
  private int minutesBanned;

  private final ConcurrentMap<String, Attempt> attemptsCache;

  public LoginAttemptServiceImpl() {
    attemptsCache = new ConcurrentHashMap<>();
  }

  @Override
  public void registerFailedAttempt(WebAuthenticationDetails details) {
    attemptsCache.compute(details.getRemoteAddress(), (key, value) -> Objects.isNull(value) ? buildFailedAttempt(1) : increaseAttempt(value));
  }

  @Override
  public void registerSuccessAttempt(WebAuthenticationDetails details) {
    attemptsCache.remove(details.getRemoteAddress());
  }

  @Override
  public boolean isBlocked(WebAuthenticationDetails details) {
    return attemptsCache.containsKey(details.getRemoteAddress()) ? !(getRemainingAttempts(details) > 0) : false;
  }

  @Override
  public void removeExpiredAttempts() {
    attemptsCache.entrySet().removeIf(this::isExpired);
  }

  private boolean isExpired(Entry<String, Attempt> attempts) {
    return Duration.between(attempts.getValue().getTime(), LocalDateTime.now()).toMinutes() >= minutesBanned;
  }

  private int getRemainingAttempts(WebAuthenticationDetails details) {
    return loginAttempts - attemptsCache.get(details.getRemoteAddress()).getFailedAttempts();
  }

  private Attempt increaseAttempt(Attempt a) {
    return buildFailedAttempt(a.getFailedAttempts() + 1);
  }

  private Attempt buildFailedAttempt(int attempt) {
    Attempt a = new Attempt();
    a.setFailedAttempts(attempt);
    a.setTime(LocalDateTime.now());
    return a;
  }

  @Data
  class Attempt {
    private int failedAttempts;
    private LocalDateTime time;
  }
}
