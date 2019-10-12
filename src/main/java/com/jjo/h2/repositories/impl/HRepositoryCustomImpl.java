package com.jjo.h2.repositories.impl;

import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.jjo.h2.model.H;
import com.jjo.h2.model.QH;
import com.jjo.h2.repositories.HRepositoryCustom;
import com.jjo.h2.utils.Utils;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HRepositoryCustomImpl extends DSLQueryCustomImpl implements HRepositoryCustom {

  private final @NonNull EntityManager em;

  @Override
  public Page<H> filter(H hFilter, Pageable pageable) {
    JPAQuery<H> query = new JPAQuery<>(em);
    QH h = QH.h;

    query.from(h);

    Optional.ofNullable(hFilter.getName()).ifPresent(name -> query.where(h.name.containsIgnoreCase(name)));
    Optional.ofNullable(hFilter.getUrl()).ifPresent(url -> query.where(h.url.containsIgnoreCase(url)));
    Optional.ofNullable(hFilter.getCover()).ifPresent(cover -> query.where(h.cover.containsIgnoreCase(cover)));
    Optional.ofNullable(hFilter.getType()).ifPresent(type -> query.where(h.type.id.eq(type.getId())));
    Utils.isNotNullOrEmpty(hFilter.getTags()).forEach(tags -> query.where(h.tags.any().id.eq(tags.getId())));

    long total = query.fetchCount();

    Optional.of(pageable).filter(Pageable::isPaged).ifPresent(p -> {
      query.limit(p.getPageSize() * (p.getPageNumber() + 1));
      query.offset(p.getOffset());
    });

    return new PageImpl<>(query.fetch(), pageable, total);
  }
}
