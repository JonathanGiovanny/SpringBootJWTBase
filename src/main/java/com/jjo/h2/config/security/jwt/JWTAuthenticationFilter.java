package com.jjo.h2.config.security.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjo.h2.config.security.SecurityConstants;
import com.jjo.h2.dto.security.LoginDTO;
import com.jjo.h2.exception.Errors;
import com.jjo.h2.exception.HException;
import com.jjo.h2.services.security.JWTService;
import com.jjo.h2.services.security.LoginAttemptService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  private final JWTService jwtService;

  private final LoginAttemptService loginAttemptService;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService, LoginAttemptService loginAttemptService) {
    super();
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.loginAttemptService = loginAttemptService;

    // This method should be called here since outside cannot be override
    super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(SecurityConstants.AUTH_LOGIN_URL, HttpMethod.POST.name()));
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      WebAuthenticationDetails details = new WebAuthenticationDetailsSource().buildDetails(request);
      if (loginAttemptService.isBlocked(details)) {
        throw new HException(Errors.BANNED_IP, details.getRemoteAddress());
      }

      // Get Data from the request
      final LoginDTO loginData = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);

      // Generate the token for the auth process
      final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginData.getUsername(), loginData.getPassword());
      token.setDetails(details);

      // Authenticate
      return authenticationManager.authenticate(token);

    } catch (IOException e) {
      log.error(Errors.MISMATCH_INPUT.getMessage(), e);
      throw new InternalAuthenticationServiceException(Errors.MISMATCH_INPUT.getMessage(), e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication)
      throws IOException, ServletException {
    // Generate token
    final String token = jwtService.generateToken(authentication);

    // Add token to header
    response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);
  }
}
