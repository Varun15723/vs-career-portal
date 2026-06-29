package com.vssolutions.careerportal.repository;

import com.vssolutions.careerportal.model.CandidateSocialLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateSocialLinkRepository extends JpaRepository<CandidateSocialLink, Long> {
    List<CandidateSocialLink> findByCandidateId(Long candidateId);
    Optional<CandidateSocialLink> findByIdAndCandidateId(Long id, Long candidateId);
    boolean existsByCandidateIdAndPlatformIgnoreCase(Long candidateId, String platform);
    void deleteByCandidateId(Long candidateId);
}
