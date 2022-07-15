package com.in28minutes.rest.webservices.restful.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ToDoDTO {
  @EqualsAndHashCode.Include
  private @NonNull Long id;
  private @NonNull String username;
  private @NonNull String description;
  private @NonNull String targetDate;
  private @NonNull Boolean done;

}
