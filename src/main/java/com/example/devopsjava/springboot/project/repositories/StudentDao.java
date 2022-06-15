package com.example.devopsjava.springboot.project.repositories;

import com.example.devopsjava.springboot.project.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentDao extends JpaRepository<Student, Long> {

    // @Query("SELECT s FROM Student s WHERE s.email = ?1;")
    Optional<Student> findStudentByEmail(String email);
}
