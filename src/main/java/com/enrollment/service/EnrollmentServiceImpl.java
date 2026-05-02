package com.enrollment.service;

import com.enrollment.model.Course;
import com.enrollment.model.Enrollment;
import com.enrollment.model.Student;
import com.enrollment.repository.EnrollmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
                                 StudentService studentService,
                                 CourseService courseService) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Enrollment findById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Enrollment not found with id: " + id));
    }

    @Override
    public Enrollment enroll(Long studentId, Long courseId, LocalDate enrollmentDate) {
        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new IllegalStateException("Student is already enrolled in this course.");
        }

        Student student = studentService.findById(studentId);
        Course course = courseService.findById(courseId);

        Enrollment enrollment = new Enrollment(student, course, enrollmentDate, null);
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public void unenroll(Long studentId, Long courseId) {
        if (!enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new EntityNotFoundException("Enrollment not found for the given student and course.");
        }
        enrollmentRepository.deleteByStudentIdAndCourseId(studentId, courseId);
    }

    @Override
    public Enrollment updateGrade(Long enrollmentId, String grade) {
        Enrollment enrollment = findById(enrollmentId);
        enrollment.setGrade(grade);
        return enrollmentRepository.save(enrollment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollment> findByStudentId(Long studentId) {
        return enrollmentRepository.findEnrollmentsWithCourseByStudentId(studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollment> findByCourseId(Long courseId) {
        return enrollmentRepository.findEnrollmentsWithStudentByCourseId(courseId);
    }
}
