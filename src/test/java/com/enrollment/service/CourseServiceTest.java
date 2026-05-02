package com.enrollment.service;

import com.enrollment.model.Course;
import com.enrollment.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course buildCourse(Long id, String code) {
        Course course = new Course("Test Course", code, "Description", 3);
        course.setId(id);
        return course;
    }

    @Test
    void findAll_returnsAllCourses() {
        when(courseRepository.findAll()).thenReturn(List.of(buildCourse(1L, "C1"), buildCourse(2L, "C2")));

        List<Course> result = courseService.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void findById_returnsCourse_whenFound() {
        Course course = buildCourse(1L, "CS101");
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Course result = courseService.findById(1L);

        assertThat(result.getCode()).isEqualTo("CS101");
    }

    @Test
    void findById_throwsEntityNotFoundException_whenNotFound() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseService.findById(99L))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessageContaining("99");
    }

    @Test
    void save_succeeds_whenCodeIsUnique() {
        Course course = buildCourse(null, "NEWCODE");
        when(courseRepository.findByCode("NEWCODE")).thenReturn(Optional.empty());
        when(courseRepository.save(course)).thenReturn(course);

        Course result = courseService.save(course);

        assertThat(result).isEqualTo(course);
        verify(courseRepository).save(course);
    }

    @Test
    void save_throwsIllegalStateException_whenCodeBelongsToDifferentCourse() {
        Course existing = buildCourse(5L, "TAKEN");
        Course newCourse = buildCourse(null, "TAKEN");

        when(courseRepository.findByCode("TAKEN")).thenReturn(Optional.of(existing));

        assertThatThrownBy(() -> courseService.save(newCourse))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("TAKEN");
    }

    @Test
    void deleteById_callsRepository_whenCourseExists() {
        when(courseRepository.existsById(1L)).thenReturn(true);

        courseService.deleteById(1L);

        verify(courseRepository).deleteById(1L);
    }

    @Test
    void deleteById_throwsEntityNotFoundException_whenCourseNotFound() {
        when(courseRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> courseService.deleteById(99L))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessageContaining("99");
    }

    @Test
    void findByStudentId_delegatesToRepository() {
        List<Course> courses = List.of(buildCourse(1L, "CS101"));
        when(courseRepository.findCoursesByStudentId(10L)).thenReturn(courses);

        List<Course> result = courseService.findByStudentId(10L);

        assertThat(result).hasSize(1);
    }
}
