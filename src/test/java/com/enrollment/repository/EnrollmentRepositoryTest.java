package com.enrollment.repository;

import com.enrollment.model.Course;
import com.enrollment.model.Enrollment;
import com.enrollment.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EnrollmentRepositoryTest {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    private Student savedStudent;
    private Course savedCourse;

    @BeforeEach
    void setUp() {
        savedStudent = studentRepository.save(
            new Student("Bob", "Lee", "bob@test.com", LocalDate.of(2002, 6, 20))
        );
        savedCourse = courseRepository.save(
            new Course("Algorithms", "ALG101", "Sorting and searching", 4)
        );
    }

    @Test
    void existsByStudentIdAndCourseId_returnsTrue_whenEnrollmentExists() {
        enrollmentRepository.save(new Enrollment(savedStudent, savedCourse, LocalDate.now(), null));

        boolean exists = enrollmentRepository.existsByStudentIdAndCourseId(
            savedStudent.getId(), savedCourse.getId()
        );

        assertThat(exists).isTrue();
    }

    @Test
    void existsByStudentIdAndCourseId_returnsFalse_whenNoEnrollment() {
        boolean exists = enrollmentRepository.existsByStudentIdAndCourseId(
            savedStudent.getId(), savedCourse.getId()
        );
        assertThat(exists).isFalse();
    }

    @Test
    void findEnrollmentsWithCourseByStudentId_fetchesCourseJoin() {
        enrollmentRepository.save(new Enrollment(savedStudent, savedCourse, LocalDate.now(), "A"));

        List<Enrollment> result = enrollmentRepository
            .findEnrollmentsWithCourseByStudentId(savedStudent.getId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCourse().getTitle()).isEqualTo("Algorithms");
    }

    @Test
    void deleteByStudentIdAndCourseId_removesEnrollment() {
        enrollmentRepository.save(new Enrollment(savedStudent, savedCourse, LocalDate.now(), null));

        enrollmentRepository.deleteByStudentIdAndCourseId(savedStudent.getId(), savedCourse.getId());

        boolean exists = enrollmentRepository.existsByStudentIdAndCourseId(
            savedStudent.getId(), savedCourse.getId()
        );
        assertThat(exists).isFalse();
    }
}
