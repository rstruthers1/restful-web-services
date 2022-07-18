package com.in28minutes.rest.webservices.restful.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TODO")
public class ToDo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "TARGET_DATE")
  private Date targetDate;

  @Column(name = "DONE")
  private Boolean done;

  @ManyToOne
  @JoinColumn(name = "USER_ID", nullable = false)
  private UserCredentials user;
}
