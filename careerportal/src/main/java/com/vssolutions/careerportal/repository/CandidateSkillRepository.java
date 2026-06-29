package com.vssolutions.careerportal.repository;

import com.vssolutions.careerportal.model.CandidateSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, Long> {
    List<CandidateSkill> findByCandidateId(Long candidateId);
    Optional<CandidateSkill> findByIdAndCandidateId(Long id, Long candidateId);
    boolean existsByCandidateIdAndSkillNameIgnoreCase(Long candidateId, String skillName);
    void deleteByCandidateId(Long candidateId);
}
