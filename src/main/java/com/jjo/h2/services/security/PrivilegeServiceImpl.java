package com.jjo.h2.services.security;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.jjo.h2.model.security.Privilege;
import com.jjo.h2.repositories.security.PrivilegeRepository;
import com.jjo.h2.utils.Utils;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

  private final @NotNull PrivilegeRepository privilegeRepo;

  @Override
  public Privilege getPrivilege(Long id) {
    return privilegeRepo.findById(id).orElseThrow();
  }

  @Override
  public List<Privilege> findAll(Pageable pageable) {
    return privilegeRepo.findAll(pageable).getContent();
  }

  @Override
  public Privilege savePrivilege(Privilege privilege) {
    return privilegeRepo.save(privilege);
  }

  @Override
  public Privilege updatePrivilege(Long id, Privilege privilegeDto) {
    Privilege privilege = privilegeRepo.findById(id).orElseThrow();
    privilege.setName(Utils.isNotNullOr(privilegeDto.getName(), privilege.getName()));
    privilege.setDescription(Utils.isNotNullOr(privilegeDto.getDescription(), privilege.getDescription()));
    return privilegeRepo.save(privilege);
  }

  @Override
  public void deletePrivilege(Long id) {
    privilegeRepo.deleteById(id);
  }
}
