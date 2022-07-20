package com.in28minutes.rest.webservices.restful.controller;

import com.in28minutes.rest.webservices.restful.dto.RoleDTO;
import com.in28minutes.rest.webservices.restful.dto.UserCredentialsDTO;
import com.in28minutes.rest.webservices.restful.model.Role;
import com.in28minutes.rest.webservices.restful.model.UserCredentials;
import com.in28minutes.rest.webservices.restful.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

  private final UserService userService;
  private final ModelMapper userCredentialsModelToDTOMapper;

  @GetMapping("/users")
  public ResponseEntity<List<UserCredentialsDTO>> getUsers() {
    List<UserCredentials> userCredentialsList = userService.getUsers();
    List<UserCredentialsDTO> userCredentialsDTOList = new ArrayList<>();
    for (UserCredentials userCredentials : userCredentialsList) {
      UserCredentialsDTO userCredentialsDTO = userCredentialsModelToDTOMapper.map(userCredentials,
          UserCredentialsDTO.class);
      userCredentialsDTOList.add(userCredentialsDTO);
    }
    return ResponseEntity.ok().body(userCredentialsDTOList);
  }

  @PostMapping("/role")
  public ResponseEntity<Role> saveRole(@RequestBody RoleDTO roleDTO) {
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role").toUriString());
    Role role = Role.builder()
        .name(roleDTO.getName())
        .build();
    return ResponseEntity.created(uri).body(userService.saveRole(role));
  }

  @PostMapping("/role/addtouser")
  public ResponseEntity<Object> addRoleToUser(@RequestBody RoleToUserForm form) {
    userService.addRoleToUser(form.getUsername(), form.getRoleName());
    return ResponseEntity.ok().build();
  }
}

@Data
class RoleToUserForm {

  private String username;
  private String roleName;
}

