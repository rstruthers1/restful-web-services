package com.in28minutes.rest.webservices.restful.dto;

public class HelloWorldDTO {

  private String message;

  public HelloWorldDTO(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return String.format("HelloWorldBean [message=%s]", message);
  }

}
