package com.enrollment.service;

import com.enrollment.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> findAll();

    Student findById(Long id);

    Student save(Student student);

    void deleteById(Long id);

    Optional<Student> findByEmail(String email);

    List<Student> search(String query);
}
