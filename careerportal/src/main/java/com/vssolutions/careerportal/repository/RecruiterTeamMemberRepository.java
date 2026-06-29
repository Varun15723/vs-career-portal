package com.vssolutions.careerportal.repository;

import com.vssolutions.careerportal.model.RecruiterTeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecruiterTeamMemberRepository extends JpaRepository<RecruiterTeamMember, Long> {
    List<RecruiterTeamMember> findByRecruiterProfileId(Long recruiterProfileId);
    List<RecruiterTeamMember> findByRecruiterProfileIdAndIsActiveTrue(Long recruiterProfileId);
    Optional<RecruiterTeamMember> findByIdAndRecruiterProfileId(Long id, Long recruiterProfileId);
    boolean existsByRecruiterProfileIdAndEmailIgnoreCase(Long recruiterProfileId, String email);
}
