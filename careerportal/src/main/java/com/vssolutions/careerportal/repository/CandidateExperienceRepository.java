package com.vssolutions.careerportal.repository;

import com.vssolutions.careerportal.model.CandidateExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateExperienceRepository extends JpaRepository<CandidateExperience, Long> {
    List<CandidateExperience> findByCandidateId(Long candidateId);
    Optional<CandidateExperience> findByIdAndCandidateId(Long id, Long candidateId);
    void deleteByCandidateId(Long candidateId);
}
