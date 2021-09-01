package com.slowed.reddity.repository;

import com.slowed.reddity.model.Subreddity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubreddityRepository extends JpaRepository<Subreddity, Long> {

  Optional<Subreddity> findByName(String subreddityName);

}
