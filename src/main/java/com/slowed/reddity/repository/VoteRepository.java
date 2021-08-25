package com.slowed.reddity.repository;

import com.slowed.reddity.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {



}
