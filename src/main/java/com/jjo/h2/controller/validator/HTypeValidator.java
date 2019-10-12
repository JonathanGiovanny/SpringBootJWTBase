package com.jjo.h2.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.jjo.h2.dto.HTypeDTO;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HTypeValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return HTypeDTO.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
  }
}
