package com.in28minutes.rest.webservices.restful.service;

import com.in28minutes.rest.webservices.restful.model.Role;
import com.in28minutes.rest.webservices.restful.model.UserCredentials;

import java.util.List;

public interface UserService {
  UserCredentials saveUser(UserCredentials user);
  Role saveRole(Role role);
  void addRoleToUser(String username, String roleName);
  UserCredentials getUser(String username);
  List<UserCredentials> getUsers();
}
