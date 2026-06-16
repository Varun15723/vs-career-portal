package com.vssolutions.careerportal.repository;

import com.vssolutions.careerportal.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Optional<Candidate> findByEmail(String email);
    Boolean existsByEmail(String email);
}