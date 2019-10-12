package com.jjo.h2.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "H_TYPE")
public class HType implements Serializable {

  private static final long serialVersionUID = -2802154356808555113L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "HT_ID")
  private Integer id;

  @Column(name = "HT_NAME", nullable = false, unique = true)
  private String name;
}
