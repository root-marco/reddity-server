package com.slowed.reddity.mapper;

import com.slowed.reddity.dto.PostRequest;
import com.slowed.reddity.dto.PostResponse;
import com.slowed.reddity.entity.Post;
import com.slowed.reddity.entity.Subreddity;
import com.slowed.reddity.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

  @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
  @Mapping(target = "subreddity", source = "subreddity")
  @Mapping(target = "user", source = "user")
  @Mapping(target = "description", source = "postRequest.description")
  Post map(PostRequest postRequest, Subreddity subreddity, User user);

  @Mapping(target = "postId", source = "postId")
  @Mapping(target = "postName", source = "postName")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "url", source = "url")
  @Mapping(target = "subreddityName", source = "subreddity.name")
  @Mapping(target = "userName", source = "user.username")
  PostResponse mapToDTO(Post post);

}