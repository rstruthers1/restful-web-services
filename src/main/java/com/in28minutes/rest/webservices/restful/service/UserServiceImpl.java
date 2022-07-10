package com.in28minutes.rest.webservices.restful.service;

import com.in28minutes.rest.webservices.restful.model.Role;
import com.in28minutes.rest.webservices.restful.model.UserCredentials;
import com.in28minutes.rest.webservices.restful.repo.RoleRepository;
import com.in28minutes.rest.webservices.restful.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  @Override
  public UserCredentials saveUser(UserCredentials user) {
    return userRepository.save(user);
  }

  @Override
  public Role saveRole(Role role) {
    return roleRepository.save(role);
  }

  @Override
  public void addRoleToUser(String username, String roleName) {
    UserCredentials user = userRepository.findByUsername(username);
    Role role = roleRepository.findByName(roleName);
    user.getRoles().add(role);
    userRepository.save(user);
  }

  @Override
  public UserCredentials getUser(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public List<UserCredentials> getUsers() {
    return userRepository.findAll();
  }


}
