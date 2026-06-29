package com.vssolutions.careerportal.repository;

import com.vssolutions.careerportal.model.CandidateResumeVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateResumeVersionRepository extends JpaRepository<CandidateResumeVersion, Long> {
    List<CandidateResumeVersion> findByCandidateId(Long candidateId);
    Optional<CandidateResumeVersion> findByIdAndCandidateId(Long id, Long candidateId);
    void deleteByCandidateId(Long candidateId);
}
