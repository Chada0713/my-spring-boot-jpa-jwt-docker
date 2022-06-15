package com.example.devopsjava.springboot.project.repositories;

import com.example.devopsjava.springboot.project.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    @Test
    void itShouldCheckIfUserExistByUsername() {
        //given
        String username = "admin";
        //when
        Boolean existsUsername = userRepo.userExists(username);
        //then
        assertThat(existsUsername).isTrue();
    }
}