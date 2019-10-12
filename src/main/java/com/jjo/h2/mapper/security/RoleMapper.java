package com.jjo.h2.mapper.security;

import java.util.List;
import org.mapstruct.Mapper;
import com.jjo.h2.dto.security.RoleDTO;
import com.jjo.h2.model.security.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

  Role dtoToEntity(RoleDTO dto);

  RoleDTO entityToDto(Role entity);

  List<RoleDTO> entityToDto(List<Role> entity);
}
