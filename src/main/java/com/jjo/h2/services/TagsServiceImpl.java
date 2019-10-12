package com.jjo.h2.services;

import java.util.Arrays;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.jjo.h2.exception.Either;
import com.jjo.h2.exception.Errors;
import com.jjo.h2.exception.HException;
import com.jjo.h2.model.Tags;
import com.jjo.h2.repositories.TagsRepository;
import com.jjo.h2.utils.Utils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagsServiceImpl implements TagsService {

  private final @NonNull TagsRepository tagsRepo;

  @Override
  public Either<?, Tags> getTag(Long id) {
    return Either.of(Utils.throwNotExistingElement(Arrays.asList("id", id)), tagsRepo.findById(id));
  }

  @Override
  public Page<Tags> findAll(Pageable pageable) {
    return tagsRepo.findAll(pageable);
  }

  @Override
  public Either<?, Tags> findByName(String name) {
    return Either.of(Utils.throwNotExistingElement(Arrays.asList("name", name)), tagsRepo.findByNameIgnoreCase(name));
  }

  @Override
  public Boolean isNameAvailable(Long id, String name) {
    Optional<Tags> foundTag = tagsRepo.findByNameIgnoreCase(name);
    return !foundTag.map(Tags::getId).filter(foundId -> !foundId.equals(id)).isPresent();
  }

  @Override
  public Page<Tags> findByNameLike(String name, Pageable pageable) {
    return tagsRepo.findByNameLikeIgnoreCase(name, pageable);
  }

  @Override
  public Tags saveTag(Tags tag) {
    return Optional.of(tag)
        .filter(t -> validateTagNameUnique(t.getId(), t.getName()))
        .map(tagsRepo::save).get();
  }

  @Override
  public Tags updateTag(Long id, Tags tag) {
    return saveTag(tag);
  }

  @Override
  public void deleteTag(Long id) {
    tagsRepo.deleteById(id);
  }

  private boolean validateTagNameUnique(Long id, String name) {
    Optional<Tags> existingH = tagsRepo.findByNameIgnoreCase(name);
    if (existingH.isPresent() && !existingH.get().getId().equals(id)) {
      throw new HException(Errors.FIELD_SHOULD_UNIQUE, "name");
    }
    return true;
  }
}
