package com.in28minutes.rest.webservices.restful.controller;

import com.in28minutes.rest.webservices.restful.dto.ToDoDTO;
import com.in28minutes.rest.webservices.restful.service.ToDoHardCodedService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Slf4j
//@PreAuthorize("hasRole('USER')")
public class ToDoController {
  private final ToDoHardCodedService toDoService;

  public ToDoController(ToDoHardCodedService toDoService) {
    this.toDoService = toDoService;
  }

  
  @GetMapping("/users/{username}/todos")
  public List<ToDoDTO> getAllToDos(@PathVariable String username) {
    return toDoService.findAll();
  }

 
  @GetMapping("/users/{username}/todos/{id}")
  public ToDoDTO getToDo(@PathVariable String username,
      @PathVariable long id) {
    return toDoService.findById(id);
  }

  
  @DeleteMapping("/users/{username}/todos/{id}") 
  public ResponseEntity<Void> deleteToDo(@PathVariable String username,
      @PathVariable Long id) {
    ToDoDTO toDoDTO = toDoService.deleteById(id);
    if (toDoDTO != null) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

 
  @PutMapping("/users/{username}/todos/{id}")
  public ResponseEntity<ToDoDTO> updateToDo(@PathVariable String username,
      @PathVariable long id, @RequestBody ToDoDTO toDoDTO) {
    log.info("updateToDo: " + toDoDTO);
    return new ResponseEntity<ToDoDTO>(toDoService.save(toDoDTO), HttpStatus.OK);
  }

  
  @PostMapping("/users/{username}/todos")
  public ResponseEntity<Void> createToDo(@PathVariable String username, @RequestBody ToDoDTO toDoDTO) {
    System.out.println("toDoDTO: " + toDoDTO);
    ToDoDTO createdToDoDTO = toDoService.save(toDoDTO);
    System.out.println("createdToDoDTO: " + createdToDoDTO);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(createdToDoDTO.getId())
        .toUri();
    return ResponseEntity.created(uri).build();
  }
}
