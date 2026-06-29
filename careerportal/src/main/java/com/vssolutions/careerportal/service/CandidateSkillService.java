package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.dto.CandidateSkillDTO;
import com.vssolutions.careerportal.model.CandidateSkill;
import com.vssolutions.careerportal.repository.CandidateSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CandidateSkillService {

    @Autowired
    private CandidateSkillRepository skillRepository;

    public CandidateSkill addSkill(Long candidateId, CandidateSkillDTO dto) {
        if (dto.getSkillName() == null || dto.getSkillName().isBlank()) {
            throw new RuntimeException("Skill name is required");
        }
        if (skillRepository.existsByCandidateIdAndSkillNameIgnoreCase(candidateId, dto.getSkillName())) {
            throw new RuntimeException("Skill already added: " + dto.getSkillName());
        }
        CandidateSkill s = mapToEntity(new CandidateSkill(), dto);
        s.setCandidateId(candidateId);
        return skillRepository.save(s);
    }

    public List<CandidateSkill> getSkills(Long candidateId) {
        return skillRepository.findByCandidateId(candidateId);
    }

    public CandidateSkill updateSkill(Long id, Long candidateId, CandidateSkillDTO dto) {
        CandidateSkill existing = skillRepository.findByIdAndCandidateId(id, candidateId)
            .orElseThrow(() -> new RuntimeException("Skill not found or doesn't belong to you"));
        return skillRepository.save(mapToEntity(existing, dto));
    }

    public void deleteSkill(Long id, Long candidateId) {
        CandidateSkill existing = skillRepository.findByIdAndCandidateId(id, candidateId)
            .orElseThrow(() -> new RuntimeException("Skill not found or doesn't belong to you"));
        skillRepository.delete(existing);
    }

    private CandidateSkill mapToEntity(CandidateSkill s, CandidateSkillDTO dto) {
        if (dto.getSkillName() != null) s.setSkillName(dto.getSkillName());
        if (dto.getProficiencyLevel() != null) s.setProficiencyLevel(dto.getProficiencyLevel());
        if (dto.getYearsOfExperience() != null) s.setYearsOfExperience(dto.getYearsOfExperience());
        if (dto.getCategory() != null) s.setCategory(dto.getCategory());
        return s;
    }
}
