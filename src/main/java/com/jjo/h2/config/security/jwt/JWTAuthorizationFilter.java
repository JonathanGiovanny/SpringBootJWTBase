package com.jjo.h2.config.security.jwt;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import com.jjo.h2.config.security.SecurityConstants;
import com.jjo.h2.services.security.JWTService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

  private final @NonNull JWTService jwtService;

  private final @NonNull UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    SecurityContextHolder.getContext().setAuthentication(null);
    final String token = getToken(request, response);

    if (jwtService.validateToken(token, request)) {
      final String username = jwtService.getClaims(token).getSubject();

      final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    chain.doFilter(request, response);
  }

  /**
   * Get the security token from the request
   * 
   * @param request Http Request
   * @return Token
   */
  private String getToken(HttpServletRequest request, HttpServletResponse response) {
    final String bearerToken = request.getHeader(SecurityConstants.TOKEN_HEADER);
    return Optional.ofNullable(bearerToken) //
        .filter(token -> !token.isBlank()) //
        .filter(token -> token.startsWith(SecurityConstants.TOKEN_PREFIX)) //
        .map(token -> token.substring(SecurityConstants.TOKEN_PREFIX.length(), token.length())) //
        .orElse("");
  }
}
