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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    private Student savedStudent;
    private Course savedCourse;

    @BeforeEach
    void setUp() {
        savedStudent = studentRepository.save(
            new Student("Alice", "Brown", "alice@test.com", LocalDate.of(2001, 3, 15))
        );
        savedCourse = courseRepository.save(
            new Course("Data Structures", "DS101", "Trees and graphs", 4)
        );
    }

    @Test
    void findByCode_returnsCourse_whenCodeExists() {
        Optional<Course> result = courseRepository.findByCode("DS101");
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Data Structures");
    }

    @Test
    void findByCode_returnsEmpty_whenCodeNotFound() {
        Optional<Course> result = courseRepository.findByCode("UNKNOWN");
        assertThat(result).isEmpty();
    }

    @Test
    void findCoursesByStudentId_returnsEnrolledCourses() {
        enrollmentRepository.save(new Enrollment(savedStudent, savedCourse, LocalDate.now(), null));

        List<Course> result = courseRepository.findCoursesByStudentId(savedStudent.getId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCode()).isEqualTo("DS101");
    }

    @Test
    void findCoursesByStudentId_returnsEmpty_whenStudentNotEnrolled() {
        List<Course> result = courseRepository.findCoursesByStudentId(savedStudent.getId());
        assertThat(result).isEmpty();
    }

    @Test
    void findByCreditsGreaterThanEqual_returnsCorrectCourses() {
        courseRepository.save(new Course("Easy Course", "EASY1", "Low credit", 1));

        List<Course> result = courseRepository.findByCreditsGreaterThanEqual(4);

        assertThat(result).allMatch(c -> c.getCredits() >= 4);
    }
}
