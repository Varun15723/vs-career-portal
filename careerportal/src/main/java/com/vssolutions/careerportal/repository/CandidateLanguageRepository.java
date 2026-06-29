package com.vssolutions.careerportal.repository;

import com.vssolutions.careerportal.model.CandidateLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateLanguageRepository extends JpaRepository<CandidateLanguage, Long> {
    List<CandidateLanguage> findByCandidateId(Long candidateId);
    Optional<CandidateLanguage> findByIdAndCandidateId(Long id, Long candidateId);
    boolean existsByCandidateIdAndLanguageIgnoreCase(Long candidateId, String language);
    void deleteByCandidateId(Long candidateId);
}
