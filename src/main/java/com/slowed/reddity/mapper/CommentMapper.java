package com.slowed.reddity.mapper;
import com.slowed.reddity.dto.CommentDTO;
import com.slowed.reddity.entity.Comment;
import com.slowed.reddity.entity.Post;
import com.slowed.reddity.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "text", source = "commentDTO.text")
  @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
  @Mapping(target = "post", source = "post")
  @Mapping(target = "user", source = "user")
  Comment map(CommentDTO commentDTO, Post post, User user);

  @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
  @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
  CommentDTO mapToDTO(Comment comment);

}