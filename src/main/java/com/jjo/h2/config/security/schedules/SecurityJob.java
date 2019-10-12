package com.jjo.h2.config.security.schedules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.jjo.h2.services.security.LoginAttemptService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class SecurityJob {

  @Autowired
  private final @NonNull LoginAttemptService loginAttemptService;

  @Scheduled(cron = "${h.config.ip.cronBannedIps}")
  public void deleteBannedIps() {
    loginAttemptService.removeExpiredAttempts();
    log.info("Cleanning banned ips");
  }
}
