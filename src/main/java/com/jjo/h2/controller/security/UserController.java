package com.jjo.h2.controller.security;

import static com.jjo.h2.config.security.SecurityConstants.ADMIN;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jjo.h2.config.security.SecurityConstants;
import com.jjo.h2.dto.security.SingUpDTO;
import com.jjo.h2.dto.security.UserDTO;
import com.jjo.h2.exception.Errors;
import com.jjo.h2.exception.HException;
import com.jjo.h2.mapper.security.UserMapper;
import com.jjo.h2.services.security.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(SecurityConstants.SECURITY_PATH)
public class UserController {

  private final UserService userService;

  private final UserMapper mapper;

  @GetMapping("/singup/checkname/{username}")
  public ResponseEntity<Boolean> checkUsernameOrEmailAvailability(@PathVariable String username) {
    return ResponseEntity.ok(userService.availableUsernameOrEmail(username, username));
  }
  
  @PostMapping("/singup")
  public ResponseEntity<Void> registerUser(@RequestBody SingUpDTO user) {
    if (!userService.availableUsernameOrEmail(user.getUsername(), user.getEmail())) {
      throw new HException(Errors.EXISTING_USER);
    }
    return ResponseEntity.created(URI.create(userService.registerUser(mapper.singUpToUser(user)).toString())).build();
  }

  @GetMapping("/users")
  @PreAuthorize(ADMIN)
  public ResponseEntity<List<UserDTO>> getUsers(Pageable pageable) {
    return ResponseEntity.ok(userService.getUsers(pageable).stream().map(mapper::entityToDto).collect(Collectors.toList()));
  }

  @PatchMapping("/users/{username}")
  public ResponseEntity<UserDTO> updateUser(@PathVariable String username, @RequestBody UserDTO user) {
    return ResponseEntity.ok(mapper.entityToDto(userService.updateUser(username, mapper.dtoToEntity(user))));
  }

  @PatchMapping("/users/{username}/change-password")
  public ResponseEntity<Void> updatePassword(@PathVariable String username, @RequestBody String password) {
    userService.updatePassword(username, password);
    return ResponseEntity.ok().build();
  }
}
