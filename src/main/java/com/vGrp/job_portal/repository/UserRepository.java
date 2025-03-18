package com.vGrp.job_portal.repository;

import com.vGrp.job_portal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);  // Use Optional to avoid null issues
}
