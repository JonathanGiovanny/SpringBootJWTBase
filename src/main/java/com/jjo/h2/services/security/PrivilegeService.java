package com.jjo.h2.services.security;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.jjo.h2.model.security.Privilege;

public interface PrivilegeService {

  Privilege getPrivilege(Long id);

  List<Privilege> findAll(Pageable pageable);

  Privilege savePrivilege(Privilege privilege);

  Privilege updatePrivilege(Long id, Privilege privilegeDto);

  void deletePrivilege(Long id);
}
