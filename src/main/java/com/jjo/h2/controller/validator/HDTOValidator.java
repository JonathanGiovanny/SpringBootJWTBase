package com.jjo.h2.controller.validator;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.jjo.h2.dto.HDTO;
import com.jjo.h2.dto.TagsDTO;
import com.jjo.h2.exception.Either;
import com.jjo.h2.services.HService;
import com.jjo.h2.services.HTypeService;
import com.jjo.h2.services.TagsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HDTOValidator implements Validator {

  private static final String TYPE_ID_VALUE = "type.id";
  private static final String TAG_ID_VALUE = "tags[%s].id";
  
  private final @NonNull HService hService;

  private final @NonNull TagsService tagsService;

  private final @NonNull HTypeService typeService;

  @Override
  public boolean supports(Class<?> clazz) {
    return HDTO.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    HDTO dto = (HDTO) target;

    validateExistingTags(dto.getTags(), errors);
    validateExistingType(dto, errors);
  }

  private void validateExistingTags(List<TagsDTO> tags, Errors errors) {
    if (Objects.nonNull(tags)) {
      IntStream.range(0, tags.size())
      .forEach(index -> {
        TagsDTO tag = tags.get(index);
        validateExistingTag(tag, index, errors);
      });
    }
  }

  private void validateExistingTag(TagsDTO tag, int index, Errors errors) {
    if (tagsService.getTag(tag.getId()).isLeft()) {
      String tagField = String.format(TAG_ID_VALUE, index);
      registerError(errors, tagField, com.jjo.h2.exception.Errors.NO_DATA);
    }
  }

  private void validateExistingType(HDTO dto, Errors errors) {
    Optional.of(dto)
    .map(HDTO::getType)
    .map(t -> typeService.getHType(t.getId()))
    .filter(Either::isLeft)
    .ifPresent(either -> registerError(errors, TYPE_ID_VALUE, com.jjo.h2.exception.Errors.NO_DATA));
  }

//  @PreFilter private boolean validateHUrlUnique(Long id, String url) {
//    Boolean existingH = hService.isUrlAvailable(id, url);
//    if (existingH.isPresent() && !existingH.get().getId().equals(id)) {
//      throw new HException(Errors.FIELD_SHOULD_UNIQUE, URL);
//    }
//    return true;
//  }

  private void registerError(Errors errors, String field, com.jjo.h2.exception.Errors error, Object... args) {
    String code = String.format(error.getCode(), args);
    String message = String.format(error.getMessage(), args);
    errors.rejectValue(field, code, message);
  }
}
