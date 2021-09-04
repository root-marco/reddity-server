package com.slowed.reddity.mapper;

import com.slowed.reddity.dto.SubreddityDTO;
import com.slowed.reddity.entity.Post;
import com.slowed.reddity.entity.Subreddity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubreddityMapper {

  @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddity.getPosts()))")
  @Mapping(target = "subreddityId", source = "subreddityId")
  SubreddityDTO mapSubreddityToDTO(Subreddity subreddity);

  default Integer mapPosts(List<Post> numberOfPosts) {

    return numberOfPosts.size();

  }

  @InheritInverseConfiguration
  @Mapping(target = "posts", ignore = true)
  Subreddity mapDTOToSubreddity(SubreddityDTO subredditDto);

}
