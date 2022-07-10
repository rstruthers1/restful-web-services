package com.in28minutes.rest.webservices.restful.service;

import com.in28minutes.rest.webservices.restful.dto.ToDoDTO;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ToDoHardCodedService {

  private static final List<ToDoDTO> toDos = new ArrayList<>();
  private static Long idCounter = 0L;


  public static final String USERNAME = "in28Minutes";

  static {
    Date currentDate = new Date();
    SimpleDateFormat todoDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    String currentDateString = todoDateFormatter.format(currentDate);
    toDos.add(new ToDoDTO(++idCounter, USERNAME, "Clean litter boxes",
        currentDateString, false));
    toDos.add(new ToDoDTO(++idCounter, USERNAME, "use JWTs",
        currentDateString, false));
    toDos.add(new ToDoDTO(++idCounter, USERNAME, "Persist ToDos to database",
        currentDateString, false));
    toDos.add(new ToDoDTO(++idCounter, USERNAME, "Users can only see their own ToDos",
        currentDateString, false));
  }
  
  public List<ToDoDTO> findAll() {
    return toDos;
  }
  
  public ToDoDTO deleteById(long id) {
    ToDoDTO toDoDTO= findById(id);
    if (toDoDTO == null) {
      return null;
    }
    toDos.remove(toDoDTO);
    return toDoDTO;
  }

  public ToDoDTO save(ToDoDTO toDoDTO) {
    if ( toDoDTO.getId() == null || toDoDTO.getId() == -1) {
      toDoDTO.setId(++idCounter);
      toDos.add(toDoDTO);
      return toDoDTO;
    } else {
      ToDoDTO existingToDoDTO = findById(toDoDTO.getId());
      if (existingToDoDTO == null) {
        return null;
      }
      existingToDoDTO.setDone(toDoDTO.getDone());
      existingToDoDTO.setDescription(toDoDTO.getDescription());
      existingToDoDTO.setTargetDate(toDoDTO.getTargetDate());
      return existingToDoDTO;
    }
  }

  public ToDoDTO findById(long id) {
    return toDos.stream()
        .filter(todo -> todo.getId() == id)
        .findFirst()
        .orElse(null);
  }

}
