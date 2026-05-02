package com.enrollment.repository;

import com.enrollment.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByCode(String code);

    @Query("SELECT DISTINCT c FROM Course c JOIN c.enrollments e JOIN e.student s WHERE s.id = :studentId")
    List<Course> findCoursesByStudentId(@Param("studentId") Long studentId);

    List<Course> findByCreditsGreaterThanEqual(Integer minCredits);
}
