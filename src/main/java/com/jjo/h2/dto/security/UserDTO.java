package com.jjo.h2.dto.security;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import com.jjo.h2.model.security.Role;
import com.jjo.h2.model.security.StatusEnum;
import lombok.Data;

@Data
public class UserDTO {

  private Long id;

  private String username;

  @NotNull
  private String password;

  @Email
  private String email;

  private StatusEnum status;

  private LocalDateTime createdDate;

  private LocalDate passwordDate;

  private byte[] profilePic;

  private Set<Role> roles;
}
