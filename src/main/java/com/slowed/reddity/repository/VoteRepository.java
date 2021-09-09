package com.slowed.reddity.repository;

import com.slowed.reddity.entity.Post;
import com.slowed.reddity.entity.User;
import com.slowed.reddity.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

  Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

}
