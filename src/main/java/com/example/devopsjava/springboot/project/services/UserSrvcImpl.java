package com.example.devopsjava.springboot.project.services;

import com.example.devopsjava.springboot.project.entities.Role;
import com.example.devopsjava.springboot.project.entities.User;
import com.example.devopsjava.springboot.project.repositories.RoleRepo;
import com.example.devopsjava.springboot.project.repositories.UserRepo;

import java.util.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserSrvcImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User userEntity) {
        Optional<User> user = userRepo.findByUsername(userEntity.getUsername());
        if(user.isPresent()){
            throw new IllegalStateException("Username taken");
        }
        log.info("Saving new user {} to the database", userEntity.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepo.save(userEntity);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        Role roleByName = roleRepo.findByName(role.getName());
        if(roleByName != null) {
            throw new IllegalStateException(String.format("Role %s already exists", role.getName()));
        }
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        Optional<User> user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);

        if(!user.get().getRoles().contains(role)){
            user.get().getRoles().add(role);
        }else{
           throw new IllegalStateException(String.format("Role %s already assigned to this user",roleName));
        }
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepo.findByUsername(username).get();
    }

    @Override
    public Boolean userExists(String username) {
        return userRepo.userExists(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetch all users");
        return userRepo.findAll();
    }

    @Override
    public void deleteUserById(Long id) {
        log.info("User with id {} has been delete in the database", id);
       userRepo.deleteById(id);
    }

    @Transactional
    @Override
    public User updateUser(Long userId, String name, String userName) {
            User user = userRepo.findById(userId).orElseThrow(
                    () -> new IllegalStateException(String.format("User with id %s not found!", userId))
            );
            if (name != null && name.length() > 0 && !Objects.equals(user.getName(), name)) {
                user.setName(name);
            }

            if (userName != null && userName.length() > 0 && !Objects.equals(user.getUsername(), userName)) {
                Optional<User> userEntityOptional = userRepo.findByUsername(userName);
                if(userEntityOptional.isPresent()){
                    throw new IllegalStateException("Username taken");
                }
                user.setUsername(userName);
            }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(username);
        if(!user.isPresent()){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }else{
            log.info("User {} is found in the database.", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.get().getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), authorities);
    }
}
