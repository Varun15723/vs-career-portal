package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.dto.RecruiterTeamMemberDTO;
import com.vssolutions.careerportal.model.RecruiterTeamMember;
import com.vssolutions.careerportal.repository.RecruiterTeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecruiterTeamMemberService {

    @Autowired
    private RecruiterTeamMemberRepository teamMemberRepository;

    public RecruiterTeamMember inviteMember(Long recruiterProfileId, RecruiterTeamMemberDTO dto) {
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new RuntimeException("Email is required");
        }
        if (teamMemberRepository.existsByRecruiterProfileIdAndEmailIgnoreCase(recruiterProfileId, dto.getEmail())) {
            throw new RuntimeException("This person is already invited to your team");
        }
        RecruiterTeamMember m = mapToEntity(new RecruiterTeamMember(), dto);
        m.setRecruiterProfileId(recruiterProfileId);
        if (m.getRole() == null) m.setRole("MEMBER");
        return teamMemberRepository.save(m);
    }

    public List<RecruiterTeamMember> getActiveTeamMembers(Long recruiterProfileId) {
        return teamMemberRepository.findByRecruiterProfileIdAndIsActiveTrue(recruiterProfileId);
    }

    public List<RecruiterTeamMember> getAllTeamMembers(Long recruiterProfileId) {
        return teamMemberRepository.findByRecruiterProfileId(recruiterProfileId);
    }

    public RecruiterTeamMember updateMemberRole(Long id, Long recruiterProfileId, String role) {
        RecruiterTeamMember member = teamMemberRepository.findByIdAndRecruiterProfileId(id, recruiterProfileId)
            .orElseThrow(() -> new RuntimeException("Team member not found or doesn't belong to your company"));
        member.setRole(role);
        return teamMemberRepository.save(member);
    }

    public RecruiterTeamMember confirmJoin(Long id, Long recruiterProfileId) {
        RecruiterTeamMember member = teamMemberRepository.findByIdAndRecruiterProfileId(id, recruiterProfileId)
            .orElseThrow(() -> new RuntimeException("Team member not found or doesn't belong to your company"));
        member.setJoinedAt(LocalDateTime.now());
        return teamMemberRepository.save(member);
    }

    // Soft delete - keeps history, just deactivates
    public void removeMember(Long id, Long recruiterProfileId) {
        RecruiterTeamMember member = teamMemberRepository.findByIdAndRecruiterProfileId(id, recruiterProfileId)
            .orElseThrow(() -> new RuntimeException("Team member not found or doesn't belong to your company"));
        member.setIsActive(false);
        teamMemberRepository.save(member);
    }

    private RecruiterTeamMember mapToEntity(RecruiterTeamMember m, RecruiterTeamMemberDTO dto) {
        if (dto.getFullName() != null) m.setFullName(dto.getFullName());
        if (dto.getEmail() != null) m.setEmail(dto.getEmail());
        if (dto.getDesignation() != null) m.setDesignation(dto.getDesignation());
        if (dto.getRole() != null) m.setRole(dto.getRole());
        return m;
    }
}
