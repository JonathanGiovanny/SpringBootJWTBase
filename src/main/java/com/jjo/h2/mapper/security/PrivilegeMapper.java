package com.jjo.h2.mapper.security;

import java.util.List;
import org.mapstruct.Mapper;
import com.jjo.h2.dto.security.PrivilegeDTO;
import com.jjo.h2.model.security.Privilege;

@Mapper(componentModel = "spring")
public interface PrivilegeMapper {

  Privilege dtoToEntity(PrivilegeDTO dto);

  PrivilegeDTO entityToDto(Privilege entity);

  List<PrivilegeDTO> entityToDto(List<Privilege> entity);
}
