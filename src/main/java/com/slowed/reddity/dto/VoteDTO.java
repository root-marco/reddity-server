package com.slowed.reddity.dto;

import com.slowed.reddity.entity.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO {

  private VoteType voteType;

  private Long postId;

}
