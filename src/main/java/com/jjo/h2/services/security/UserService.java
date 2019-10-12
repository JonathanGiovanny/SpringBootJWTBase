package com.jjo.h2.services.security;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.jjo.h2.model.security.User;

public interface UserService {

  Boolean availableUsernameOrEmail(String username, String email);

  void incrementUserAttempt(String username);
  
  void userAttemptReset(String username);

  List<User> getUsers(Pageable pageable);

  Long registerUser(User user);

  User updateUser(String username, User user);

  void updatePassword(String username, String password);
}
