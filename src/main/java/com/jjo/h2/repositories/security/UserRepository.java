package com.jjo.h2.repositories.security;

import java.util.Optional;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import com.jjo.h2.model.security.User;

public interface UserRepository extends Neo4jRepository<User, Long> {

  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

  Optional<User> findByUsernameOrEmail(String username, String email, @Depth int depth);
}
