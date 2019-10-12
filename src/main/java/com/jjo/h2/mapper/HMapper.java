package com.jjo.h2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import com.jjo.h2.dto.HDTO;
import com.jjo.h2.model.H;

@Mapper(componentModel = "spring")
public interface HMapper {

  @Mapping(target = "clicks", ignore = true)
  @Mapping(target = "modifiedDate", ignore = true)
  H dtoToEntity(HDTO dto);

  HDTO entityToDTO(H entity);

  default Page<HDTO> entityToDTO(Page<H> entity) {
    return entity.map(this::entityToDTO);
  }
}
