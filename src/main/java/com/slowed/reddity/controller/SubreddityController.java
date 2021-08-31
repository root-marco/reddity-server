package com.slowed.reddity.controller;

import com.slowed.reddity.dto.SubreddityDTO;
import com.slowed.reddity.service.SubreddityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subreddity")
@AllArgsConstructor
@Slf4j
public class SubreddityController {

  private final SubreddityService subreddityService;

  @PostMapping
  public ResponseEntity<SubreddityDTO> createSubreddity(@RequestBody SubreddityDTO subreddityDTO) {

    return ResponseEntity.status(HttpStatus.CREATED)
      .body(subreddityService.save(subreddityDTO));

  }

  @GetMapping
  public ResponseEntity<Object> getAllSubreddity() {

    return ResponseEntity.status(HttpStatus.OK)
      .body(subreddityService.getAll());

  }

  @GetMapping("/{id}")
  public ResponseEntity<SubreddityDTO> getSubreddity(@PathVariable Long id) {

    return ResponseEntity.status(HttpStatus.OK)
      .body(subreddityService.getSubreddity(id));

  }

}
