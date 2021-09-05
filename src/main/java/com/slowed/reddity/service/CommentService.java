package com.slowed.reddity.service;

import com.slowed.reddity.dto.CommentDTO;
import com.slowed.reddity.entity.Comment;
import com.slowed.reddity.entity.NotificationEmail;
import com.slowed.reddity.entity.Post;
import com.slowed.reddity.entity.User;
import com.slowed.reddity.exceptions.PostNotFoundException;
import com.slowed.reddity.mapper.CommentMapper;
import com.slowed.reddity.repository.CommentRepository;
import com.slowed.reddity.repository.PostRepository;
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
public class CommentService {

  private static final String POST_URL = "";

  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final AuthService authService;
  private final CommentMapper commentMapper;
  private final CommentRepository commentRepository;
  private final MailContentBuilder mailContentBuilder;
  private final MailService mailService;

  public void createComment(CommentDTO commentDTO) {

    Post post = postRepository.findById(commentDTO.getPostId())
      .orElseThrow(() -> new PostNotFoundException(commentDTO.getPostId().toString()));

    User user = authService.getCurrentUser();

    Comment comment = commentMapper.map(commentDTO, post, user);

    commentRepository.save(comment);

    String message = mailContentBuilder.build(authService.getCurrentUser().getUsername() +
      " posted a comment on your post." + POST_URL);

    sendCommentNotification(message, post.getUser());

  }

  private void sendCommentNotification(String message, User user) {

    mailService.sendMail(new NotificationEmail(user.getUsername() +
      " Commented on your post", user.getEmail(), message));

  }

  public List<CommentDTO> getAllCommentsForPost(Long postId) {

    Post post = postRepository.findById(postId)
      .orElseThrow(() -> new PostNotFoundException(postId.toString()));

    return commentRepository
      .findByPost(post)
      .stream()
      .map(commentMapper::mapToDTO)
      .collect(toList());
  }

  public List<CommentDTO> getAllCommentsForUser(String userName) {

    User user = userRepository.findByUsername(userName)
      .orElseThrow(() -> new UsernameNotFoundException(userName));

    return commentRepository
      .findAllByUser(user)
      .stream()
      .map(commentMapper::mapToDTO)
      .collect(toList());

  }

}
