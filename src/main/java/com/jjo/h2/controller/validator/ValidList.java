package com.jjo.h2.controller.validator;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Documented
@Constraint(validatedBy = {ValidListImpl.class})
@Target({METHOD, PARAMETER})
@Retention(RUNTIME)
public @interface ValidList {

  String message() default "invalid elements in the List";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  Class<? extends Validator> customValidator() default DefaultValidator.class;

}


/**
 * Default empty class when the ValidList has not send a validator
 */
final class DefaultValidator implements Validator {
  @Override
  public boolean supports(Class<?> clazz) {
    return false;
  }

  @Override
  public void validate(Object target, Errors errors) {
    throw new RuntimeException();
  }
}
