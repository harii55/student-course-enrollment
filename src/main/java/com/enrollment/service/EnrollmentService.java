package com.enrollment.service;

import com.enrollment.model.Enrollment;

import java.time.LocalDate;
import java.util.List;

public interface EnrollmentService {

    List<Enrollment> findAll();

    Enrollment findById(Long id);

    Enrollment enroll(Long studentId, Long courseId, LocalDate enrollmentDate);

    void unenroll(Long studentId, Long courseId);

    Enrollment updateGrade(Long enrollmentId, String grade);

    List<Enrollment> findByStudentId(Long studentId);

    List<Enrollment> findByCourseId(Long courseId);
}
