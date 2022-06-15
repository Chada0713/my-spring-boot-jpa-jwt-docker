package com.example.devopsjava.springboot.project.controllers;

import com.example.devopsjava.springboot.project.entities.Student;
import com.example.devopsjava.springboot.project.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping()
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("{id}")
    public Optional<Student> getById(@PathVariable("id") Long id){
        return Optional.ofNullable(studentService.getStudentById(id).orElseThrow(()
                -> new IllegalStateException("Student not found")));
    }

    @PostMapping()
    public ResponseEntity addStudent(@RequestBody Student student){
        studentService.insertStudent(student);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{studentId}")
    public ResponseEntity updateStudent(@PathVariable("studentId") Long studentId,
                                        @RequestParam(required = false) String name,
                                        @RequestParam(required = false) String email){
        studentService.updateStudent(studentId, name, email);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }
}
