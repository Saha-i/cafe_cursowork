package com.example.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cafe.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginIgnoreCase(String login);

    boolean existsByLoginIgnoreCase(String login);
}
