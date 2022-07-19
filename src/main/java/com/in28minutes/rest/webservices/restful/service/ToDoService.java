package com.in28minutes.rest.webservices.restful.service;

import com.in28minutes.rest.webservices.restful.Constants;
import com.in28minutes.rest.webservices.restful.dto.ToDoDTO;
import com.in28minutes.rest.webservices.restful.model.ToDo;
import com.in28minutes.rest.webservices.restful.model.UserCredentials;
import com.in28minutes.rest.webservices.restful.repo.ToDoRepository;
import com.in28minutes.rest.webservices.restful.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ToDoService {

  private final ToDoRepository toDoRepository;
  private final UserRepository userRepository;
  private final ModelMapper toDoModelToDTOMapper;

  public ToDoService(ToDoRepository toDoRepository, UserRepository userRepository,
      ModelMapper toDoModelToDTOMapper) {
    this.toDoRepository = toDoRepository;
    this.userRepository = userRepository;
    this.toDoModelToDTOMapper = toDoModelToDTOMapper;
  }

  public List<ToDoDTO> findAllForUser(String username) {
    UserCredentials user = userRepository.findByUsername(username);
    List<ToDo> toDos = user.getToDos();
    List<ToDoDTO> toDoDTOList = new ArrayList<>();
    for (ToDo toDo : toDos) {
      ToDoDTO toDoDTO = toDoModelToDTOMapper.map(toDo, ToDoDTO.class);
      toDoDTOList.add(toDoDTO);
    }
    return toDoDTOList;
  }

  public ToDoDTO createForUser(String username, ToDoDTO toDoDTO) throws ParseException {
    UserCredentials user = userRepository.findByUsername(username);
    ToDo toDo = ToDo.builder()
        .description(toDoDTO.getDescription())
        .done(toDoDTO.getDone())
        .targetDate(Constants.TODO_DATE_FORMATTER.parse(toDoDTO.getTargetDate()))
        .build();

    toDo.setUser(user);
    ToDo newToDo = toDoRepository.save(toDo);
    return toDoModelToDTOMapper.map(newToDo, ToDoDTO.class);
  }

  public ToDoDTO deleteByIdForUser(String username, long todoId) {
    UserCredentials user = userRepository.findByUsername(username);
    Long userId = user.getId();
    ToDo toDo = toDoRepository.findByIdAndUserId(todoId, userId);
    if (toDo == null) {
      return null;
    }
    Long deleteUserId = toDoRepository.deleteByIdAndUserId(todoId, userId);
    if (deleteUserId == null) {
      return null;
    }
    return toDoModelToDTOMapper.map(toDo, ToDoDTO.class);
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
    return toDoModelToDTOMapper.map(toDo, ToDoDTO.class);
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
    toDo.setTargetDate(Constants.TODO_DATE_FORMATTER.parse(toDoDTO.getTargetDate()));
    toDoRepository.save(toDo);
    return toDoModelToDTOMapper.map(toDo, ToDoDTO.class);
  }
}
