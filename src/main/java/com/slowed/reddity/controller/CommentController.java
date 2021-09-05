package com.slowed.reddity.controller;

import com.slowed.reddity.dto.CommentDTO;
import com.slowed.reddity.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment/")
@AllArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @PostMapping
  public ResponseEntity<Void> createComment(@RequestBody CommentDTO commentDTO) {

    commentService.createComment(commentDTO);

    return new ResponseEntity<>(HttpStatus.CREATED);

  }

  @GetMapping("/by-post/{postId}")
  public ResponseEntity<List<CommentDTO>>
    getAllCommentsForPost(@PathVariable Long postId) {

    return ResponseEntity.status(HttpStatus.OK)
      .body(commentService.getAllCommentsForPost(postId));

  }

  @GetMapping("/by-user/{userName}")
  public ResponseEntity<List<CommentDTO>>
    getAllCommentsForUser(@PathVariable String userName){

    return ResponseEntity.status(HttpStatus.OK)
      .body(commentService.getAllCommentsForUser(userName));

  }

}
