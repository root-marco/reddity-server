package com.slowed.reddity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_table")
public class User {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long userId;

  @NotBlank(message = "username is required")
  private String username;

  @NotBlank(message = "password is required")
  private String password;

  @Email
  @NotEmpty(message = "email is required")
  private String email;

  private Instant created;

  private boolean enabled;

}
