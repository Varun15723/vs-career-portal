package com.vssolutions.careerportal.repository;

import com.vssolutions.careerportal.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByIsActiveTrue();
    List<Job> findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCase(String title, String company);
    List<Job> findByPostedById(Long recruiterId);
}