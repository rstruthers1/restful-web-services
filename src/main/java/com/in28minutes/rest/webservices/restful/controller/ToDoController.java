package com.in28minutes.rest.webservices.restful.controller;

import com.in28minutes.rest.webservices.restful.dto.ToDoDTO;
import com.in28minutes.rest.webservices.restful.service.ToDoHardCodedService;
import com.in28minutes.rest.webservices.restful.service.ToDoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.text.ParseException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Slf4j
//@PreAuthorize("hasRole('USER')")
public class ToDoController {
  private final ToDoService toDoService;

  public ToDoController(ToDoService toDoService) {
    this.toDoService = toDoService;
  }

  
  @GetMapping("/users/{username}/todos")
  public List<ToDoDTO> getAllToDos(@PathVariable String username) {
    return toDoService.findAllForUser(username);
  }

 
  @GetMapping("/users/{username}/todos/{id}")
  public ToDoDTO getToDo(@PathVariable String username,
      @PathVariable long id) {
    return toDoService.findByIdForUser(id, username);
  }

  
  @DeleteMapping("/users/{username}/todos/{id}") 
  public ResponseEntity<Void> deleteToDo(@PathVariable String username,
      @PathVariable Long id) {
    ToDoDTO toDoDTO = toDoService.deleteByIdForUser(username, id);
    if (toDoDTO != null) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

 
  @PutMapping("/users/{username}/todos/{id}")
  public ResponseEntity<ToDoDTO> updateToDo(@PathVariable String username,
      @PathVariable long id, @RequestBody ToDoDTO toDoDTO) {
    log.info("updateToDo: " + toDoDTO);
    ToDoDTO updatedToDoDTO = null;
    try {
      updatedToDoDTO = toDoService.updateForUser(toDoDTO, username);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
    return new ResponseEntity<ToDoDTO>(updatedToDoDTO, HttpStatus.OK);
  }

  
  @PostMapping("/users/{username}/todos")
  public ResponseEntity<Void> createToDo(@PathVariable String username, @RequestBody ToDoDTO toDoDTO) {
    System.out.println("toDoDTO: " + toDoDTO);
    ToDoDTO createdToDoDTO = null;
    try {
      createdToDoDTO = toDoService.createForUser(username, toDoDTO);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
    System.out.println("createdToDoDTO: " + createdToDoDTO);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(createdToDoDTO.getId())
        .toUri();
    return ResponseEntity.created(uri).build();
  }
}
