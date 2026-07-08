package com.vssolutions.careerportal.repository;

import com.vssolutions.careerportal.model.CourseEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, Long> {
    List<CourseEnrollment> findByCandidateId(Long candidateId);
    Optional<CourseEnrollment> findByCandidateIdAndCourseId(Long candidateId, Long courseId);
    long countByCourseId(Long courseId);
}
