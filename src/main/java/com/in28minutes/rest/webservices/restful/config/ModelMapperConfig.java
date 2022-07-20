package com.in28minutes.rest.webservices.restful.config;

import com.in28minutes.rest.webservices.restful.Constants;
import com.in28minutes.rest.webservices.restful.dto.ToDoDTO;
import com.in28minutes.rest.webservices.restful.dto.UserCredentialsDTO;
import com.in28minutes.rest.webservices.restful.model.ToDo;
import com.in28minutes.rest.webservices.restful.model.UserCredentials;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class ModelMapperConfig {

  @Bean
  public ModelMapper toDoModelToDTOMapper() {
    ModelMapper modelMapper = new ModelMapper();
    Converter<Date, String> formatDate = ctx -> ctx.getSource() != null
        ? Constants.TODO_DATE_FORMATTER.format(ctx.getSource())
        : "";
    modelMapper.typeMap(ToDo.class, ToDoDTO.class)
        .addMappings(mapper -> mapper.using(formatDate).map(ToDo::getTargetDate, ToDoDTO::setTargetDate));
    return modelMapper;
  }
  
  @Bean
  public ModelMapper userCredentialsModelToDTOMapper() {
    ModelMapper modelMapper = new ModelMapper();
    Converter<Date, String> masker = ctx -> "***********";
    modelMapper.typeMap(UserCredentials.class, UserCredentialsDTO.class)
        .addMappings(mapper -> mapper.using(masker).map(UserCredentials::getPassword, UserCredentialsDTO::setPassword));
    return modelMapper;
  }


  
}
