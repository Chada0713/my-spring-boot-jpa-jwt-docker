package com.example.devopsjava.springboot.project.services;

import com.example.devopsjava.springboot.project.entities.Role;
import com.example.devopsjava.springboot.project.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    Boolean userExists(String username);
    List<User> getUsers();
    void deleteUserById(Long id);
    User updateUser(Long userId, String name, String userName);
}
