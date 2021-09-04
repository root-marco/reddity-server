package com.slowed.reddity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long commentId;

  @NotEmpty
  private String text;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "postId", referencedColumnName = "postId")
  private Post post;

  private Instant createdDate;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "userId", referencedColumnName = "userId")
  private User user;

}