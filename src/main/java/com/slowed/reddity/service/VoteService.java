package com.slowed.reddity.service;

import com.slowed.reddity.dto.VoteDTO;
import com.slowed.reddity.entity.Post;
import com.slowed.reddity.entity.Vote;
import com.slowed.reddity.exceptions.PostNotFoundException;
import com.slowed.reddity.exceptions.SpringReddityException;
import com.slowed.reddity.repository.PostRepository;
import com.slowed.reddity.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.slowed.reddity.entity.VoteType.UPVOTE;


@Service
@AllArgsConstructor
public class VoteService {

  private final VoteRepository voteRepository;
  private final PostRepository postRepository;
  private final AuthService authService;

  @Transactional
  public void vote(VoteDTO voteDTO) {

    Post post = postRepository.findById(voteDTO.getPostId())
      .orElseThrow(() -> new PostNotFoundException("post not found with id - " + voteDTO.getPostId()));

    Optional<Vote> voteByPostAndUser = voteRepository
      .findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());

    if (voteByPostAndUser.isPresent()
      && voteByPostAndUser.get().getVoteType()
      .equals(voteDTO.getVoteType())) {

      throw new SpringReddityException("You have already "
        + voteDTO.getVoteType() + "'d for this post");

    }

    if (UPVOTE.equals(voteDTO.getVoteType())) {
      post.setVoteCount(post.getVoteCount() + 1);
    } else {
      post.setVoteCount(post.getVoteCount() - 1);
    }

    voteRepository.save(mapToVote(voteDTO, post));

    postRepository.save(post);

  }

  private Vote mapToVote(VoteDTO voteDTO, Post post) {

    return Vote
      .builder()
      .voteType(voteDTO.getVoteType())
      .post(post)
      .user(authService.getCurrentUser())
      .build();

  }

}
