package com.jjo.h2.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.jjo.h2.dto.HTypeDTO;
import com.jjo.h2.model.HType;

@Mapper(componentModel = "spring")
public interface HTypeMapper {

  HType dtoToEntity(HTypeDTO dto);

  HTypeDTO entityToDto(HType entity);

  List<HTypeDTO> entityToDto(List<HType> entity);
}
