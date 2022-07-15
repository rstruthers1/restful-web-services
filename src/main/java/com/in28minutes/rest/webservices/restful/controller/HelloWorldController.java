package com.in28minutes.rest.webservices.restful.controller;

import com.in28minutes.rest.webservices.restful.dto.HelloWorldDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="*")
//@PreAuthorize("hasRole('ADMIN')")
public class HelloWorldController {
  @GetMapping(path = "/hello-world")
  public String helloWorld() {
    return "Hello World!";
  }

  @GetMapping(path = "/hello-world-bean")
  public HelloWorldDTO helloWorldBean() {
    return new HelloWorldDTO("Hello World Bean!");
  }

  ///hello-world/path-variable/in28minutes
  @GetMapping(path = "/hello-world/{name}")
  public HelloWorldDTO helloWorldPathVariable(@PathVariable String name) {
//    throw new RuntimeException("Something went wrong");
    return new HelloWorldDTO(String.format("Hello World, %s", name));
  }
}
