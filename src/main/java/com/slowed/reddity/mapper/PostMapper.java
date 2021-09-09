package com.slowed.reddity.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.slowed.reddity.dto.PostRequest;
import com.slowed.reddity.dto.PostResponse;
import com.slowed.reddity.entity.*;
import com.slowed.reddity.repository.CommentRepository;
import com.slowed.reddity.repository.VoteRepository;
import com.slowed.reddity.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.slowed.reddity.entity.VoteType.UPVOTE;
import static com.slowed.reddity.entity.VoteType.DOWNVOTE;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

  @Autowired
  private CommentRepository commentRepository;
  @Autowired
  private VoteRepository voteRepository;
  @Autowired
  private AuthService authService;

  @Mapping(target = "user", source = "user")
  @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
  @Mapping(target = "description", source = "postRequest.description")
  @Mapping(target = "subreddity", source = "subreddity")
  @Mapping(target = "voteCount", constant = "0")
  public abstract Post map(PostRequest postRequest, Subreddity subreddity, User user);

  @Mapping(target = "postId", source = "postId")
  @Mapping(target = "subreddityName", source = "subreddity.name")
  @Mapping(target = "userName", source = "user.username")
  @Mapping(target = "commentCount", expression = "java(commentCount(post))")
  @Mapping(target = "duration", expression = "java(getDuration(post))")
  @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
  @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
  public abstract PostResponse mapToDTO(Post post);

  Integer commentCount(Post post) {

    return commentRepository
      .findByPost(post).size();

  }

  String getDuration(Post post) {

    return TimeAgo
      .using(post.getCreatedDate()
      .toEpochMilli());

  }

  boolean isPostUpVoted(Post post) {

    return checkVoteType(post, UPVOTE);

  }

  boolean isPostDownVoted(Post post) {

    return checkVoteType(post, DOWNVOTE);

  }

  private boolean checkVoteType(Post post, VoteType voteType) {

    if (authService.isLoggedIn()) {

      Optional<Vote> voteForPostByUser = voteRepository
        .findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());

      return voteForPostByUser.filter(vote ->
        vote.getVoteType().equals(voteType)).isPresent();

    }

    return false;

  }

}
