package com.enrollment.service;

import com.enrollment.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> findAll();

    Course findById(Long id);

    Course save(Course course);

    void deleteById(Long id);

    Optional<Course> findByCode(String code);

    List<Course> findByStudentId(Long studentId);
}
