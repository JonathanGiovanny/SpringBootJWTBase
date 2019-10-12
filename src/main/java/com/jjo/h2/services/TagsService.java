package com.jjo.h2.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.jjo.h2.exception.Either;
import com.jjo.h2.model.Tags;

public interface TagsService {

  Either<?, Tags> getTag(Long id);

  Page<Tags> findAll(Pageable pageable);

  Boolean isNameAvailable(Long id, String name);

  Either<?, Tags> findByName(String name);

  Page<Tags> findByNameLike(String name, Pageable pageable);

  Tags saveTag(Tags tag);

  Tags updateTag(Long id, Tags tagDto);

  void deleteTag(Long id);
}
