package com.jjo.h2.services.security;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.jjo.h2.exception.Errors;
import com.jjo.h2.model.security.AccessData;
import com.jjo.h2.model.security.Privilege;
import com.jjo.h2.model.security.Role;
import com.jjo.h2.model.security.StatusEnum;
import com.jjo.h2.model.security.User;
import com.jjo.h2.repositories.security.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

  private final @NonNull UserRepository userRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepo.findByUsernameOrEmail(username, username, 2).map(this::buildUserDetails)
        .orElseThrow(() -> new UsernameNotFoundException(Errors.MISSING_USER.getMessage()));
  }

  /**
   * Generate UserDetails based on the User entity we have
   * 
   * @param user
   * @return
   */
  private UserDetails buildUserDetails(User user) {
    UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
    builder.password(user.getPassword());
    Set<GrantedAuthority> authorities = getGrantedRoles(user.getRoles());
    authorities.addAll(getGrantedAuthorities(user.getRoles()));
    builder.authorities(authorities);

    builder.accountLocked(StatusEnum.B.equals(user.getStatus()));
    builder.disabled(StatusEnum.I.equals(user.getStatus()));

    return builder.build();
  }

  /**
   * Create the authorities for the privileges list
   * 
   * @param roles
   * @return
   */
  private Set<GrantedAuthority> getGrantedAuthorities(Set<Role> roles) {
    return roles.stream().map(r -> Optional.ofNullable(r.getPrivileges()).orElse(Set.of())) //
        .flatMap(Set::stream) //
        .map(AccessData::getPrivilege) //
        .map(Privilege::getName).distinct() //
        .map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
  }

  /**
   * The role has to be added as an authority
   * 
   * @param roles
   * @return
   */
  private Set<GrantedAuthority> getGrantedRoles(Set<Role> roles) {
    return roles.stream().map(Role::getName).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
  }
}
