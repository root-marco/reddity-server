package com.slowed.reddity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

  private Long commentId;

  private Long postId;

  private Instant createdDate;

  private String text;

  private String userName;

}
