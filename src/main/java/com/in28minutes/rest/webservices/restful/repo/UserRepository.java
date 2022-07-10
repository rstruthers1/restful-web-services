package com.in28minutes.rest.webservices.restful.repo;

import com.in28minutes.rest.webservices.restful.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserCredentials, Long> {
  UserCredentials findByUsername(String username);
}
