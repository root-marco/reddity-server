package com.slowed.reddity.controller;

import com.slowed.reddity.dto.VoteDTO;
import com.slowed.reddity.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vote")
@AllArgsConstructor
public class VoteController {

  private final VoteService voteService;

  @PostMapping
  public ResponseEntity<Void> vote(@RequestBody VoteDTO voteDTO) {

    voteService.vote(voteDTO);

    return new ResponseEntity<>(HttpStatus.OK);

  }

}
