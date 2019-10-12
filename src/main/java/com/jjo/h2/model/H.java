package com.jjo.h2.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.jjo.h2.model.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "H")
public class H extends Auditable implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 4709089064220157044L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "H_ID")
  private Long id;

  @Column(name = "H_NAME")
  private String name;

  @Column(name = "H_URL", nullable = false, unique = true)
  private String url;

  @Column(name = "H_COVER")
  private String cover;

  @Builder.Default
  @Column(name = "H_CLICKS")
  private Long clicks = 0L;

  @Builder.Default
  @Column(name = "H_SCORE")
  private Double score = 0.0;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "H_TYPE__HT_ID")
  private HType type;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
  @JoinTable(name = "H_TAGS",
      joinColumns = @JoinColumn(name = "H_ID", referencedColumnName = "H_ID"),
      inverseJoinColumns = @JoinColumn(name = "TAG_ID", referencedColumnName = "TAG_ID"))
  private Set<Tags> tags;
}
