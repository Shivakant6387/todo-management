package com.example.todo.management.repository;

import com.example.todo.management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);

    Boolean existsByEmail(String email);

    Optional<User> findByUserNameOrEmail(String userName, String email);
}
