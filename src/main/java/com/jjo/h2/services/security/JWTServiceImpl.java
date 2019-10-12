package com.jjo.h2.services.security;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.jjo.h2.exception.Errors;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.TextCodec;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JWTServiceImpl implements JWTService {

  @Value("${h.config.jwtSecret}")
  private String jwtSecret;

  @Value("${h.config.jwtExpiration}")
  private int jwtExpiration;

  @Override
  public String generateToken(Authentication authentication) {
    UserDetails principal = (UserDetails) authentication.getPrincipal();
    return Jwts.builder().setIssuer(principal.getUsername()) //
        .setSubject(principal.getUsername()) //
        .setHeaderParam("typ", "JWT")
        .setIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))//
        .setExpiration(Date.from(LocalDateTime.now().plusMinutes(jwtExpiration).atZone(ZoneId.systemDefault()).toInstant())) //
        .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.decode(jwtSecret)) // Encode the secret with Base 64
        .compact();
  }

  @Override
  public boolean validateToken(final String token, HttpServletRequest request) {
    try {
      if (Objects.isNull(token) || token.isBlank()) {
        return false;
      }
      return !getClaims(token).getSubject().isBlank();

    } catch (SignatureException ex) {
      log.error("Invalid JWT signature");
    } catch (MalformedJwtException ex) {
      log.error("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      log.error("Expired JWT token");
      request.setAttribute(Errors.class.getName(), Errors.EXPIRED_TOKEN);
    } catch (UnsupportedJwtException ex) {
      log.error("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      log.error("JWT claims string is empty.");
    }
    return false;
  }

  @Override
  public Claims getClaims(String token) {
    return Jwts.parser() //
        .setSigningKey(TextCodec.BASE64.decode(jwtSecret)) //
        .parseClaimsJws(token) //
        .getBody();
  }
}
