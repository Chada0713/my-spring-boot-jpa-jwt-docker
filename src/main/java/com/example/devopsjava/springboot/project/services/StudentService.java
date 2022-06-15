package com.example.devopsjava.springboot.project.services;

import com.example.devopsjava.springboot.project.entities.Student;
import com.example.devopsjava.springboot.project.repositories.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    StudentDao studentDao;

    public List<Student> getAllStudents() {
       return studentDao.findAll();
    }

    public Optional<Student> getStudentById(Long id){
        return studentDao.findById(id);
    }

    public void insertStudent(Student student){
        Optional<Student> studentByEmail = studentDao.findStudentByEmail(student.getEmail());
        if (studentByEmail.isPresent()){
            throw new IllegalStateException("Email already taken");
        }
        studentDao.save(student);
    }

    @Transactional
    public void updateStudent(Long id, String name, String email) {
        Student student = studentDao.findById(id).orElseThrow(
                () -> new IllegalStateException(String.format("Student with id %s not found!", id))
        );

        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentOptional = studentDao.findStudentByEmail(email);
            if(studentOptional.isPresent()){
                throw new IllegalStateException("Email taken");
            }
            student.setEmail(email);
        }
    }


    public void deleteStudent(Long studentId) {
        boolean exists = studentDao.existsById(studentId);
        if(!exists)
            throw  new IllegalStateException(String.format("Student with id %s does not exists.", studentId));
       studentDao.deleteById(studentId);
    }
}
