package com.slowed.reddity.repository;

import com.slowed.reddity.entity.Post;
import com.slowed.reddity.entity.Subreddity;
import com.slowed.reddity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> findAllBySubreddity(Subreddity subreddity);

  List<Post> findByUser(User user);

}
