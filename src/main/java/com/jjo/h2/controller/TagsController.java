package com.jjo.h2.controller;

import static com.jjo.h2.config.security.SecurityConstants.DELETE_TAGS;
import static com.jjo.h2.config.security.SecurityConstants.MODIFY_TAGS;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jjo.h2.controller.validator.TagsValidator;
import com.jjo.h2.dto.TagsDTO;
import com.jjo.h2.mapper.TagsMapper;
import com.jjo.h2.services.TagsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tags")
public class TagsController {

  private final @NonNull TagsService tagsService;

  private final @NonNull TagsValidator tagsValidator;

  private final @NonNull TagsMapper mapper;

  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    binder.addValidators(tagsValidator);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TagsDTO> getTag(@PathVariable Long id) {
    return ResponseEntity.ok(mapper.entityToDto(tagsService.getTag(id).getOrElseThrow()));
  }

  @GetMapping("/search/{filter}")
  public ResponseEntity<Page<TagsDTO>> findByFilter(@PathVariable String filter, Pageable pageable) {
    return ResponseEntity.ok(tagsService.findByNameLike(filter, pageable).map(mapper::entityToDto));
  }

  @GetMapping
  public ResponseEntity<Page<TagsDTO>> findAll(Pageable pageable) {
    return ResponseEntity.ok(tagsService.findAll(pageable).map(mapper::entityToDto));
  }

  @GetMapping("/availableName/{name}")
  @PreAuthorize(MODIFY_TAGS)
  public ResponseEntity<Boolean> availableName(@PathVariable String name) {
    return ResponseEntity.ok(tagsService.isNameAvailable(null, name));
  }

  @PostMapping
  @PreAuthorize(MODIFY_TAGS)
  public ResponseEntity<Void> saveTag(@Valid @RequestBody TagsDTO tag) {
    return ResponseEntity.created(URI.create(tagsService.saveTag(mapper.dtoToEntity(tag)).getId().toString())).build();
  }

  @PutMapping("/{id}")
  @PreAuthorize(MODIFY_TAGS)
  public ResponseEntity<TagsDTO> updateTag(@PathVariable Long id, @Valid @RequestBody TagsDTO tag) {
    return ResponseEntity.ok(mapper.entityToDto(tagsService.updateTag(id, mapper.dtoToEntity(tag))));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize(DELETE_TAGS)
  public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
    tagsService.deleteTag(id);
    return ResponseEntity.noContent().build();
  }
}
