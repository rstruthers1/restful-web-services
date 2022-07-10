package com.in28minutes.rest.webservices.restful.service;

import com.in28minutes.rest.webservices.restful.model.UserCredentials;
import com.in28minutes.rest.webservices.restful.repo.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class DatabaseUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;
  private final UserDetailsMapper userDetailsMapper;

  public DatabaseUserDetailsService(
      UserRepository userRepository, UserDetailsMapper userDetailsMapper) {
    this.userRepository = userRepository;
    this.userDetailsMapper = userDetailsMapper;
  }
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserCredentials userCredentials =
        userRepository.findByUsername(username);
    return userDetailsMapper.toUserDetails(userCredentials);
  }
}
