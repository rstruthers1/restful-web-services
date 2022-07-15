package com.in28minutes.rest.webservices.restful.repo;

import com.in28minutes.rest.webservices.restful.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
  ToDo findByIdAndUserId(Long toDoId, Long userId);
  Long deleteByIdAndUserId(Long toDoId, Long userId);
}
