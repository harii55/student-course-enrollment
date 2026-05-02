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
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    private Student savedStudent;
    private Course savedCourse;

    @BeforeEach
    void setUp() {
        savedStudent = studentRepository.save(
            new Student("Jane", "Doe", "jane.doe@test.com", LocalDate.of(2000, 1, 1))
        );
        savedCourse = courseRepository.save(
            new Course("Java 101", "JAVA101", "Intro to Java", 3)
        );
    }

    @Test
    void findByEmail_returnsStudent_whenEmailExists() {
        Optional<Student> result = studentRepository.findByEmail("jane.doe@test.com");
        assertThat(result).isPresent();
        assertThat(result.get().getFirstName()).isEqualTo("Jane");
    }

    @Test
    void findByEmail_returnsEmpty_whenEmailNotFound() {
        Optional<Student> result = studentRepository.findByEmail("nobody@test.com");
        assertThat(result).isEmpty();
    }

    @Test
    void save_persistsStudentAndAssignsId() {
        Student student = new Student("Tom", "Smith", "tom@test.com", LocalDate.of(2001, 5, 10));
        Student saved = studentRepository.save(student);
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void findStudentsByCourseId_returnsEnrolledStudents() {
        enrollmentRepository.save(new Enrollment(savedStudent, savedCourse, LocalDate.now(), null));

        List<Student> result = studentRepository.findStudentsByCourseId(savedCourse.getId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getEmail()).isEqualTo("jane.doe@test.com");
    }

    @Test
    void findStudentsByCourseId_returnsEmpty_whenNobodyEnrolled() {
        List<Student> result = studentRepository.findStudentsByCourseId(savedCourse.getId());
        assertThat(result).isEmpty();
    }

    @Test
    void findByFirstNameOrLastNameContaining_returnsMatchingStudents() {
        List<Student> result = studentRepository
            .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("jane", "jane");
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getFirstName()).isEqualTo("Jane");
    }
}
