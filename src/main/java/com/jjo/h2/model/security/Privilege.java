package com.jjo.h2.model.security;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@NodeEntity
public class Privilege implements Serializable {

  /**
  * 
  */
  private static final long serialVersionUID = 244607917095337336L;

  @Id
  @GeneratedValue
  private Long id;

  private String name;

  private String description;
}
