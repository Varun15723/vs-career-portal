package com.vssolutions.careerportal.repository;

import com.vssolutions.careerportal.model.CandidateEducation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateEducationRepository extends JpaRepository<CandidateEducation, Long> {
    List<CandidateEducation> findByCandidateId(Long candidateId);
    Optional<CandidateEducation> findByIdAndCandidateId(Long id, Long candidateId);
    void deleteByCandidateId(Long candidateId);
}
