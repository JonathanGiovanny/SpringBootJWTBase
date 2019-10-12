package com.jjo.h2.controller.validator;

import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class ValidListImpl implements ConstraintValidator<ValidList, List<?>> {

  @Override
  public boolean isValid(List<?> value, ConstraintValidatorContext context) {
    return true;
  }
}
