package com.enrollment.service;

import com.enrollment.model.Student;
import com.enrollment.repository.StudentRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student buildStudent(Long id, String email) {
        Student student = new Student("John", "Doe", email, LocalDate.of(2000, 1, 1));
        student.setId(id);
        return student;
    }

    @Test
    void findAll_returnsAllStudents() {
        List<Student> students = List.of(buildStudent(1L, "a@test.com"), buildStudent(2L, "b@test.com"));
        when(studentRepository.findAll()).thenReturn(students);

        List<Student> result = studentService.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void findById_returnsStudent_whenFound() {
        Student student = buildStudent(1L, "test@test.com");
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student result = studentService.findById(1L);

        assertThat(result.getEmail()).isEqualTo("test@test.com");
    }

    @Test
    void findById_throwsEntityNotFoundException_whenNotFound() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.findById(99L))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessageContaining("99");
    }

    @Test
    void save_succeeds_whenEmailIsUnique() {
        Student student = buildStudent(null, "new@test.com");
        when(studentRepository.findByEmail("new@test.com")).thenReturn(Optional.empty());
        when(studentRepository.save(student)).thenReturn(student);

        Student result = studentService.save(student);

        assertThat(result).isEqualTo(student);
        verify(studentRepository).save(student);
    }

    @Test
    void save_throwsIllegalStateException_whenEmailBelongsToDifferentStudent() {
        Student existing = buildStudent(5L, "taken@test.com");
        Student newStudent = buildStudent(null, "taken@test.com");

        when(studentRepository.findByEmail("taken@test.com")).thenReturn(Optional.of(existing));

        assertThatThrownBy(() -> studentService.save(newStudent))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("taken@test.com");
    }

    @Test
    void deleteById_callsRepository_whenStudentExists() {
        when(studentRepository.existsById(1L)).thenReturn(true);

        studentService.deleteById(1L);

        verify(studentRepository).deleteById(1L);
    }

    @Test
    void deleteById_throwsEntityNotFoundException_whenStudentNotFound() {
        when(studentRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> studentService.deleteById(99L))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessageContaining("99");
    }
}
