package com.nurd.todolist.repos;

import com.nurd.todolist.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {
    @Query(value = "SELECT * FROM users WHERE email = ?1", nativeQuery = true)
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE username = ?1", nativeQuery = true)
    Optional<User> findByUsername(String username);
}
