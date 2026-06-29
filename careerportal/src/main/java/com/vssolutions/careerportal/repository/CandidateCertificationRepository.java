package com.vssolutions.careerportal.repository;

import com.vssolutions.careerportal.model.CandidateCertification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateCertificationRepository extends JpaRepository<CandidateCertification, Long> {
    List<CandidateCertification> findByCandidateId(Long candidateId);
    Optional<CandidateCertification> findByIdAndCandidateId(Long id, Long candidateId);
    void deleteByCandidateId(Long candidateId);
}
