package com.slowed.reddity.service;

import com.slowed.reddity.dto.SubreddityDTO;
import com.slowed.reddity.exceptions.SpringReddityException;
import com.slowed.reddity.model.Subreddity;
import com.slowed.reddity.repository.SubreddityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubreddityService {

  private final SubreddityRepository subreddityRepository;

  @Transactional
  public SubreddityDTO save(SubreddityDTO subreddityDTO) {

    Subreddity save = subreddityRepository.save(mapToSubreddit(subreddityDTO));

    subreddityDTO.setId(save.getSubreddityId());

    return subreddityDTO;

  }

  @Transactional(readOnly = true)
  public List<SubreddityDTO> getAll() {

    return subreddityRepository
      .findAll()
      .stream()
      .map(this::mapToDto)
      .collect(toList());

  }

  public SubreddityDTO getSubreddity(Long id) {

    Subreddity subreddity = subreddityRepository
      .findById(id)
      .orElseThrow(() -> new SpringReddityException("no subreddity found with ID - " + id));

    return mapToDto(subreddity);

  }

  private SubreddityDTO mapToDto(Subreddity subreddity) {

    return SubreddityDTO.builder()
      .name(subreddity.getName())
      .id(subreddity.getSubreddityId())
      .numberOfPosts(subreddity.getPosts().size())
      .build();

  }

  private Subreddity mapToSubreddit(SubreddityDTO subreddityDTO) {

    return Subreddity.builder()
      .name("/r/" + subreddityDTO.getName())
      .description(subreddityDTO.getDescription())
      .build();

  }

}
