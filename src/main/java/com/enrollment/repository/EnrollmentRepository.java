package com.enrollment.repository;

import com.enrollment.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    @Query("SELECT e FROM Enrollment e JOIN FETCH e.student JOIN FETCH e.course ORDER BY e.enrollmentDate DESC")
    List<Enrollment> findAllWithStudentAndCourse();

    @Query("SELECT e FROM Enrollment e JOIN FETCH e.course c WHERE e.student.id = :studentId ORDER BY e.enrollmentDate DESC")
    List<Enrollment> findEnrollmentsWithCourseByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT e FROM Enrollment e JOIN FETCH e.student s WHERE e.course.id = :courseId ORDER BY s.lastName ASC")
    List<Enrollment> findEnrollmentsWithStudentByCourseId(@Param("courseId") Long courseId);

    void deleteByStudentIdAndCourseId(Long studentId, Long courseId);
}
