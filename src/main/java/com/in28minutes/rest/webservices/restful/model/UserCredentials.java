package com.in28minutes.rest.webservices.restful.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "USER")
public class UserCredentials {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;
  
  @Column(name = "FIRST_NAME")
  private String firstName;

  @Column(name = "LAST_NAME")
  private String lastName;
  
  @Column(name = "USERNAME")
  private String username;
  @Column(name = "PASSWORD")
  private String password;
  
  @Column(name = "ENABLED")
  private  Boolean enabled;
  
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name="USER_ROLE",
      joinColumns=
      @JoinColumn(name="USER_ID", referencedColumnName="ID"),
      inverseJoinColumns=
      @JoinColumn(name="ROLE_ID", referencedColumnName="ID")
  )
  private List<Role> roles = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  private List<ToDo> toDos = new ArrayList<>();
  
  public List<String> getRoleNames() {
    List<String> roleNames = new ArrayList<>();
    for (Role role: roles) {
      roleNames.add(role.getName());
    }
    return roleNames;
  }
  

}
