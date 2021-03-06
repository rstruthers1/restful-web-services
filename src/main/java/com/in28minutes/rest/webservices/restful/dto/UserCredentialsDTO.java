package com.in28minutes.rest.webservices.restful.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserCredentialsDTO {
  @EqualsAndHashCode.Include
  private Long id;
  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private  Boolean enabled;
}
