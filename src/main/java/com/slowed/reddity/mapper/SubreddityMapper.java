package com.slowed.reddity.mapper;

import com.slowed.reddity.dto.SubreddityDTO;
import com.slowed.reddity.model.Post;
import com.slowed.reddity.model.Subreddity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubreddityMapper {

  @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddity.getPosts()))")
  SubreddityDTO mapSubreddityToDTO(Subreddity subreddity);

  default Integer mapPosts(List<Post> numberOfPosts) {

    return numberOfPosts.size();

  }

  @InheritInverseConfiguration
  @Mapping(target = "posts", ignore = true)
  Subreddity mapDTOToSubreddity(SubreddityDTO subredditDto);

}
