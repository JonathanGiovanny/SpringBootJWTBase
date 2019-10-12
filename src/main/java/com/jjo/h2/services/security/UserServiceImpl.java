package com.jjo.h2.services.security;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jjo.h2.config.DatasourceNeo4j;
import com.jjo.h2.exception.Errors;
import com.jjo.h2.exception.HException;
import com.jjo.h2.model.security.RolesEnum;
import com.jjo.h2.model.security.StatusEnum;
import com.jjo.h2.model.security.User;
import com.jjo.h2.repositories.security.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(value = DatasourceNeo4j.TRANSACTION_MANAGER)
public class UserServiceImpl implements UserService {

  @Value("${h.config.user.loginAttempts}")
  private int loginAttempts;

  private final @NonNull UserRepository userRepo;

  private final @NonNull RolesService roleService;

  private final @NonNull PasswordEncoder passEncoder;

  private User save(User user) {
    return userRepo.save(user);
  }

  private User getUserByUsername(String username) {
    return userRepo.findByUsername(username).get();
  }

  @Override
  public Boolean availableUsernameOrEmail(String username, String email) {
    return userRepo.findByUsernameOrEmail(username, email, 1).isEmpty();
  }

  @Override
  public void incrementUserAttempt(String username) {
    User user = getUserByUsername(username);
    int userAttempts = user.getLoginAttempts() + 1;
    user.setLoginAttempts(userAttempts);
    if (userAttempts >= loginAttempts) {
      user.setStatus(StatusEnum.B);
    }
    save(user);
  }

  @Override
  public void userAttemptReset(String username) {
    User user = getUserByUsername(username);
    user.setLoginAttempts(0);
    save(user);
  }

  @Override
  public Long registerUser(User user) {
    user.setPassword(passEncoder.encode(user.getPassword()));
    user.setRoles(Set.of(roleService.getRoleByName(RolesEnum.ROLE_USER.name())));

    return save(user).getId();
  }

  @Override
  public List<User> getUsers(Pageable pageable) {
    return userRepo.findAll(pageable).getContent();
  }

  @Override
  public User updateUser(String username, User user) {
    User entity = getUserByUsername(username);
    // copyDTO(user, entity);
    return save(entity);
  }

  @Override
  public void updatePassword(String username, String password) {
    User user = getUserByUsername(username);

    passwordValidator(user, password);

    user.setPasswordDate(LocalDate.now());
    user.setPassword(passEncoder.encode(password));
    save(user);
  }

  /**
   * Generates all the validations regarding the password rules
   * 
   * @param user
   * @param password
   * @return
   */
  private boolean passwordValidator(User user, String password) {
    // New password should not be current
    if (!passEncoder.matches(user.getPassword(), password)) {
      throw new HException(Errors.SAME_PASSWORD);
    }
    return true;
  }
}
