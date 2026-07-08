package com.vssolutions.careerportal.repository;

import com.vssolutions.careerportal.model.InternshipApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface InternshipApplicationRepository extends JpaRepository<InternshipApplication, Long> {
    List<InternshipApplication> findByCandidateId(Long candidateId);
    List<InternshipApplication> findByInternshipId(Long internshipId);
    Optional<InternshipApplication> findByCandidateIdAndInternshipId(Long candidateId, Long internshipId);
}
