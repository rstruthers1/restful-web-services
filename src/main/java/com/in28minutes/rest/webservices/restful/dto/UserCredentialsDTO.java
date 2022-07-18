package com.in28minutes.rest.webservices.restful.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialsDTO {
  private String username;
  private String password;
  private String firstName;
  private String lastName;
}
