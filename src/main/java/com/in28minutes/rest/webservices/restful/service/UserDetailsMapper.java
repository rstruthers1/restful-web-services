package com.in28minutes.rest.webservices.restful.service;


import com.in28minutes.rest.webservices.restful.model.UserCredentials;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
class UserDetailsMapper {

  UserDetails toUserDetails(UserCredentials userCredentials) {

    return User.withUsername(userCredentials.getUsername())
        .password(userCredentials.getPassword())
        .roles(userCredentials.getRoleNames().toArray(new String[0]))
        .build();
  }
}
