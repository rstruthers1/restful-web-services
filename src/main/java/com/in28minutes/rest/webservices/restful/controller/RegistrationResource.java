package com.in28minutes.rest.webservices.restful.controller;


import com.in28minutes.rest.webservices.restful.dto.UserCredentialsDto;
import com.in28minutes.rest.webservices.restful.model.Role;
import com.in28minutes.rest.webservices.restful.model.UserCredentials;
import com.in28minutes.rest.webservices.restful.repo.RoleRepository;
import com.in28minutes.rest.webservices.restful.repo.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
class RegistrationResource {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  public RegistrationResource(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.roleRepository = roleRepository;
  }

  @PostMapping("/registration")
  @ResponseStatus(code = HttpStatus.CREATED)
  public void register(@RequestBody UserCredentialsDto userCredentialsDto) {
    UserCredentials user =
        UserCredentials.builder()
            .enabled(true)
            .username(userCredentialsDto.getUsername())
            .firstName(userCredentialsDto.getFirstName())
            .lastName(userCredentialsDto.getLastName())
            .password(passwordEncoder.encode(userCredentialsDto.getPassword()))
            .build();
    
    Role userRole = roleRepository.findByName("USER");
    user.setRoles(Collections.singletonList(userRole));
    userRepository.save(user);
  }
}
