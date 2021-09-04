package com.slowed.reddity.repository;

import com.slowed.reddity.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {



}
