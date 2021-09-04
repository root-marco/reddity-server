package com.slowed.reddity.repository;

import com.slowed.reddity.entity.Comment;
import com.slowed.reddity.entity.Post;
import com.slowed.reddity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findByPost(Post post);

  List<Comment> findAllByUser(User user);

}
