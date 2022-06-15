package com.example.devopsjava.springboot.project.repositories;

import com.example.devopsjava.springboot.project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("select count(u) > 0 from User u where u.username = ?1")
    Boolean userExists(String username);
}
