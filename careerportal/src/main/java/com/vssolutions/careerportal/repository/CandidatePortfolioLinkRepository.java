package com.vssolutions.careerportal.repository;

import com.vssolutions.careerportal.model.CandidatePortfolioLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CandidatePortfolioLinkRepository extends JpaRepository<CandidatePortfolioLink, Long> {
    List<CandidatePortfolioLink> findByCandidateId(Long candidateId);
    Optional<CandidatePortfolioLink> findByIdAndCandidateId(Long id, Long candidateId);
    void deleteByCandidateId(Long candidateId);
}
