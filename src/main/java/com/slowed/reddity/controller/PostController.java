package com.slowed.reddity.controller;

import com.slowed.reddity.dto.PostRequest;
import com.slowed.reddity.dto.PostResponse;
import com.slowed.reddity.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class PostController {

  private final PostService postService;

  @PostMapping
  public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {

    postService.save(postRequest);

    return new ResponseEntity<>(HttpStatus.CREATED);

  }

  @GetMapping
  public ResponseEntity<List<PostResponse>> getAllPosts() {

    return status(HttpStatus.OK).body(postService.getAllPosts());

  }

  @GetMapping("/{id}")
  public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {

    return status(HttpStatus.OK).body(postService.getPost(id));

  }

    @GetMapping("/by-subreddity/{id}")
  public ResponseEntity<List<PostResponse>> getPostsBySubreddity(@PathVariable Long id) {

    return status(HttpStatus.OK).body(postService.getPostsBySubreddity(id));

  }

  @GetMapping("/by-user/{name}")
  public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String name) {

    return status(HttpStatus.OK).body(postService.getPostsByUsername(name));

  }

}
