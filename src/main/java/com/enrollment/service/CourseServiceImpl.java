package com.enrollment.service;

import com.enrollment.model.Course;
import com.enrollment.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));
    }

    @Override
    public Course save(Course course) {
        courseRepository.findByCode(course.getCode()).ifPresent(existing -> {
            boolean isDifferentCourse = !existing.getId().equals(course.getId());
            if (isDifferentCourse) {
                throw new IllegalStateException("Course code already in use: " + course.getCode());
            }
        });
        return courseRepository.save(course);
    }

    @Override
    public void deleteById(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new EntityNotFoundException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findByCode(String code) {
        return courseRepository.findByCode(code);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findByStudentId(Long studentId) {
        return courseRepository.findCoursesByStudentId(studentId);
    }
}
