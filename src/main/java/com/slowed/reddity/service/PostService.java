package com.slowed.reddity.service;

import com.slowed.reddity.dto.PostRequest;
import com.slowed.reddity.dto.PostResponse;
import com.slowed.reddity.exceptions.PostNotFoundException;
import com.slowed.reddity.exceptions.SubreddityNotFoundException;
import com.slowed.reddity.mapper.PostMapper;
import com.slowed.reddity.model.Post;
import com.slowed.reddity.model.Subreddity;
import com.slowed.reddity.model.User;
import com.slowed.reddity.repository.PostRepository;
import com.slowed.reddity.repository.SubreddityRepository;
import com.slowed.reddity.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

  private final PostRepository postRepository;
  private final SubreddityRepository subreddityRepository;
  private final UserRepository userRepository;
  private final AuthService authService;
  private final PostMapper postMapper;

  public void save(PostRequest postRequest) {

    Subreddity subreddity = subreddityRepository.findByName(postRequest.getSubreddityName())
      .orElseThrow(() -> new SubreddityNotFoundException(postRequest.getSubreddityName()));

    User currentUser = authService.getCurrentUser();

    postRepository.save(postMapper.map(postRequest, subreddity, currentUser));

  }

  @Transactional(readOnly = true)
  public List<PostResponse> getAllPosts() {

    return postRepository.findAll()
      .stream()
      .map(postMapper::mapToDTO)
      .collect(toList());

  }

  @Transactional(readOnly = true)
  public PostResponse getPost(Long id) {

    Post post = postRepository.findById(id)
      .orElseThrow(() -> new PostNotFoundException(id.toString()));

    return postMapper.mapToDTO(post);

  }

  @Transactional(readOnly = true)
  public List<PostResponse> getPostsBySubreddity(Long subreddityId) {

    Subreddity subreddity = subreddityRepository.findById(subreddityId)
      .orElseThrow(() -> new SubreddityNotFoundException(subreddityId.toString()));

    List<Post> posts = postRepository.findAllBySubreddity(subreddity);

    return posts.stream().map(postMapper::mapToDTO).collect(toList());

  }

  @Transactional(readOnly = true)
  public List<PostResponse> getPostsByUsername(String username) {

    User user = userRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException(username));

    return postRepository.findByUser(user)
      .stream()
      .map(postMapper::mapToDTO)
      .collect(toList());

  }

}
