package com.enrollment.service;

import com.enrollment.model.Course;
import com.enrollment.model.Enrollment;
import com.enrollment.model.Student;
import com.enrollment.repository.EnrollmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private StudentService studentService;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    private Student buildStudent(Long id) {
        Student s = new Student("Alice", "Smith", "alice@test.com", LocalDate.of(2001, 1, 1));
        s.setId(id);
        return s;
    }

    private Course buildCourse(Long id) {
        Course c = new Course("Java 101", "JAVA101", "Basics", 3);
        c.setId(id);
        return c;
    }

    @Test
    void enroll_succeeds_whenStudentNotAlreadyEnrolled() {
        when(enrollmentRepository.existsByStudentIdAndCourseId(1L, 2L)).thenReturn(false);
        when(studentService.findById(1L)).thenReturn(buildStudent(1L));
        when(courseService.findById(2L)).thenReturn(buildCourse(2L));
        when(enrollmentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Enrollment result = enrollmentService.enroll(1L, 2L, LocalDate.now());

        assertThat(result.getStudent().getId()).isEqualTo(1L);
        assertThat(result.getCourse().getId()).isEqualTo(2L);
    }

    @Test
    void enroll_throwsIllegalStateException_whenAlreadyEnrolled() {
        when(enrollmentRepository.existsByStudentIdAndCourseId(1L, 2L)).thenReturn(true);

        assertThatThrownBy(() -> enrollmentService.enroll(1L, 2L, LocalDate.now()))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("already enrolled");
    }

    @Test
    void unenroll_succeeds_whenEnrollmentExists() {
        when(enrollmentRepository.existsByStudentIdAndCourseId(1L, 2L)).thenReturn(true);

        enrollmentService.unenroll(1L, 2L);

        verify(enrollmentRepository).deleteByStudentIdAndCourseId(1L, 2L);
    }

    @Test
    void unenroll_throwsEntityNotFoundException_whenEnrollmentNotFound() {
        when(enrollmentRepository.existsByStudentIdAndCourseId(1L, 2L)).thenReturn(false);

        assertThatThrownBy(() -> enrollmentService.unenroll(1L, 2L))
            .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void updateGrade_updatesAndSavesEnrollment() {
        Enrollment enrollment = new Enrollment(buildStudent(1L), buildCourse(2L), LocalDate.now(), null);
        enrollment.setId(10L);
        when(enrollmentRepository.findById(10L)).thenReturn(Optional.of(enrollment));
        when(enrollmentRepository.save(enrollment)).thenReturn(enrollment);

        Enrollment result = enrollmentService.updateGrade(10L, "A+");

        assertThat(result.getGrade()).isEqualTo("A+");
        verify(enrollmentRepository).save(enrollment);
    }

    @Test
    void findByStudentId_delegatesToRepository() {
        List<Enrollment> list = List.of(new Enrollment());
        when(enrollmentRepository.findEnrollmentsWithCourseByStudentId(1L)).thenReturn(list);

        List<Enrollment> result = enrollmentService.findByStudentId(1L);

        assertThat(result).hasSize(1);
    }
}
