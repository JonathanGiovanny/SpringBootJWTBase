package com.jjo.h2.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.jjo.h2.exception.Either;
import com.jjo.h2.model.HType;

public interface HTypeService {

  Either<?, HType> getHType(Integer id);

  Page<HType> findAll(Pageable pageable);

  Boolean isNameAvailable(Integer id, String name);

  HType findByName(String name);

  Page<HType> findByNameLike(String name, Pageable pageable);

  HType saveHType(HType hType);

  void deleteHType(Integer id);
}
