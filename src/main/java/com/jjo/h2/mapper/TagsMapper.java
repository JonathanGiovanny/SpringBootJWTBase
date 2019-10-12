package com.jjo.h2.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.jjo.h2.dto.TagsDTO;
import com.jjo.h2.model.Tags;

@Mapper(componentModel = "spring")
public interface TagsMapper {

  Tags dtoToEntity(TagsDTO dto);

  TagsDTO entityToDto(Tags entity);

  List<TagsDTO> entityToDto(List<Tags> entity);
}
