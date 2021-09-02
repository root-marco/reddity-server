package com.slowed.reddity.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Subreddity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long subreddityId;

  @NotBlank(message = "community name is required")
  private String name;

  @NotBlank(message = "description is required")
  private String description;

  @OneToMany(fetch = LAZY)
  private List<Post> posts;

  private Instant createdDate;

  @ManyToOne(fetch = LAZY)
  private User user;

}
