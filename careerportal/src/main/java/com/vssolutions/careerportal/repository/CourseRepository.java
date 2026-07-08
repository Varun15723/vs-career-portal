package com.vssolutions.careerportal.repository;

import com.vssolutions.careerportal.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByIsActiveTrue();
    List<Course> findByCategoryIgnoreCaseAndIsActiveTrue(String category);
    List<Course> findByTitleContainingIgnoreCase(String title);
}
