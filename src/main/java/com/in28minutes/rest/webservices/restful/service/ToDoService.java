package com.in28minutes.rest.webservices.restful.service;

import com.in28minutes.rest.webservices.restful.dto.ToDoDTO;
import com.in28minutes.rest.webservices.restful.model.ToDo;
import com.in28minutes.rest.webservices.restful.model.UserCredentials;
import com.in28minutes.rest.webservices.restful.repo.ToDoRepository;
import com.in28minutes.rest.webservices.restful.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ToDoService {

  SimpleDateFormat todoDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
  private final ToDoRepository toDoRepository;
  private final UserRepository userRepository;

  public ToDoService(ToDoRepository toDoRepository, UserRepository userRepository) {
    this.toDoRepository = toDoRepository;
    this.userRepository = userRepository;
  }

  public List<ToDoDTO> findAllForUser(String username) {
    UserCredentials user = userRepository.findByUsername(username);
    List<ToDo> toDos = user.getToDos();
    List<ToDoDTO> toDoDTOList = new ArrayList<>();
    for (ToDo toDo: toDos) {
      ToDoDTO toDoDTO = ToDoDTO.builder()
          .description(toDo.getDescription())
          .id(toDo.getId())
          .username(user.getUsername())
          .done(toDo.getDone())
          .targetDate(todoDateFormatter.format(toDo.getTargetDate()))
          .build();
      toDoDTOList.add(toDoDTO);
    }
    return toDoDTOList;
  }

  public ToDoDTO createForUser(String username, ToDoDTO toDoDTO) throws ParseException {
    UserCredentials user = userRepository.findByUsername(username);
    ToDo toDo = ToDo.builder()
        .description(toDoDTO.getDescription())
        .done(toDoDTO.getDone())
        .targetDate(todoDateFormatter.parse(toDoDTO.getTargetDate()))
        .build();

    toDo.setUser(user);
    ToDo newToDo = toDoRepository.save(toDo);

    ToDoDTO newToDoDTO = ToDoDTO.builder()
        .description(newToDo.getDescription())
        .id(newToDo.getId())
        .username(user.getUsername())
        .done(newToDo.getDone())
        .targetDate(todoDateFormatter.format(newToDo.getTargetDate()))
        .build();

    return newToDoDTO;
  }

  public ToDoDTO deleteByIdForUser(String username, long todoId) {
    UserCredentials user = userRepository.findByUsername(username);
    Long userId = user.getId();
    ToDo toDo = toDoRepository.findByIdAndUserId(todoId, userId);
    if (toDo == null) {
      return null;
    }
    ToDoDTO toDoDTO = ToDoDTO.builder()
        .description(toDo.getDescription())
        .id(toDo.getId())
        .username(username)
        .done(toDo.getDone())
        .targetDate(todoDateFormatter.format(toDo.getTargetDate()))
        .build();
    Long deleteUserId = toDoRepository.deleteByIdAndUserId(todoId, userId);
    if (deleteUserId == null) {
      return null;
    }
    return toDoDTO;
  }

  public ToDoDTO findByIdForUser(long todoId, String username) {
    UserCredentials user = userRepository.findByUsername(username);
    if (user == null) {
      return null;
    }
    Long userId = user.getId();
    ToDo toDo = toDoRepository.findByIdAndUserId(todoId, userId);
    if (toDo == null) {
      return null;
    }
    ToDoDTO toDoDTO = ToDoDTO.builder()
        .description(toDo.getDescription())
        .id(toDo.getId())
        .username(username)
        .done(toDo.getDone())
        .targetDate(todoDateFormatter.format(toDo.getTargetDate()))
        .build();
    return toDoDTO;
  }

  public ToDoDTO updateForUser(ToDoDTO toDoDTO, String username) throws ParseException {
    UserCredentials user = userRepository.findByUsername(username);
    if (user == null) {
      return null;
    }
    Long userId = user.getId();
    ToDo toDo = toDoRepository.findByIdAndUserId(toDoDTO.getId(), userId);
    if (toDo == null) {
      return null;
    }
    toDo.setDescription(toDoDTO.getDescription());
    toDo.setDone(toDoDTO.getDone());
    toDo.setTargetDate(todoDateFormatter.parse(toDoDTO.getTargetDate()));
    toDoRepository.save(toDo);
    return ToDoDTO.builder()
        .description(toDo.getDescription())
        .id(toDo.getId())
        .username(username)
        .done(toDo.getDone())
        .targetDate(todoDateFormatter.format(toDo.getTargetDate()))
        .build();
  }
}
