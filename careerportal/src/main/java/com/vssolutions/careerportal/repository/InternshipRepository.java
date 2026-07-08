package com.vssolutions.careerportal.repository;

import com.vssolutions.careerportal.model.Internship;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InternshipRepository extends JpaRepository<Internship, Long> {
    List<Internship> findByIsActiveTrue();
    List<Internship> findByPostedById(Long recruiterId);
    List<Internship> findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCase(String title, String company);
}
