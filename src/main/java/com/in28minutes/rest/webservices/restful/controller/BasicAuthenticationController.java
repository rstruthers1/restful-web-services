package com.in28minutes.rest.webservices.restful.controller;

import com.in28minutes.rest.webservices.restful.dto.AuthenticationDTO;

import com.in28minutes.rest.webservices.restful.model.UserCredentials;
import com.in28minutes.rest.webservices.restful.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BasicAuthenticationController {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  
  
  public BasicAuthenticationController(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping(path = "/basicauth")
  public ResponseEntity<AuthenticationDTO> basicAuth(@RequestHeader("Authorization") String authorization) {
    if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
      // Authorization: Basic base64credentials
      String base64Credentials = authorization.substring("Basic".length()).trim();
      byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
      String credentials = new String(credDecoded, StandardCharsets.UTF_8);
      // credentials = username:password
      final String[] values = credentials.split(":", 2);
      if (values.length >= 2) {
        String username = values[0];
        String password = values[1];
       
        UserCredentials user = userService.getUser(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
          AuthenticationDTO authenticationDTO = new AuthenticationDTO("You are authenticated");
          return ResponseEntity.accepted().body(authenticationDTO);
        }
      }
    }
    AuthenticationDTO authenticationDTO = new AuthenticationDTO("Invalid credentials");
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authenticationDTO);
  }

}
