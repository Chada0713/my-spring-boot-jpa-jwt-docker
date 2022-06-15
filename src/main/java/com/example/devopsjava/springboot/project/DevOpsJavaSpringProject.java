package com.example.devopsjava.springboot.project;

import com.example.devopsjava.springboot.project.entities.Role;
import com.example.devopsjava.springboot.project.entities.User;
import com.example.devopsjava.springboot.project.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class DevOpsJavaSpringProject {

	public static void main(String[] args) {
		SpringApplication.run(DevOpsJavaSpringProject.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	/*@Bean
	CommandLineRunner run(UserService userService){
		return args -> {
			//add default roles
			userService.saveRole(new Role(null, "USER_ROLE"));
			userService.saveRole(new Role(null, "ADMIN_ROLE"));
			userService.saveRole(new Role(null, "SUPER_ADMIN_ROLE"));

			// add super admin user
			userService.saveUser(new User(null, "Super Admin", "admin", "admin123", new ArrayList<>()));

			// add role to user
			userService.addRoleToUser("admin", "USER_ROLE");
			userService.addRoleToUser("admin", "ADMIN_ROLE");
			userService.addRoleToUser("admin", "SUPER_ADMIN_ROLE");
		};
	}*/
}
