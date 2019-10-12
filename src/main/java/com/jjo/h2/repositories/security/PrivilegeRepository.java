package com.jjo.h2.repositories.security;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import com.jjo.h2.model.security.Privilege;

public interface PrivilegeRepository extends Neo4jRepository<Privilege, Long> {

}
