package com.slowed.reddity.repository;

import com.slowed.reddity.model.Post;
import com.slowed.reddity.model.Subreddity;
import com.slowed.reddity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> findAllBySubreddity(Subreddity subreddity);

  List<Post> findByUser(User user);

}
