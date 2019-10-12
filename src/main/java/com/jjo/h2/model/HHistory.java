package com.jjo.h2.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "H_HISTORY")
@EntityListeners(AuditingEntityListener.class)
public class HHistory implements Serializable {

  private static final long serialVersionUID = -8462244913061213684L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "HH_ID")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "HH_H_ID", nullable = false)
  private H h;

  @CreatedBy
  @Column(name = "HH_CREATED_BY")
  private String creationBy;

  @CreatedDate
  @Column(name = "HH_DATE", nullable = false, updatable = false)
  private LocalDateTime date;
}
