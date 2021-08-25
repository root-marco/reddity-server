package com.slowed.reddity.repository;

import com.slowed.reddity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {



}
