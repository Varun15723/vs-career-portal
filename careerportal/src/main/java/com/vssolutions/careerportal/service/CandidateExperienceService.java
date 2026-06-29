package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.dto.CandidateExperienceDTO;
import com.vssolutions.careerportal.model.CandidateExperience;
import com.vssolutions.careerportal.repository.CandidateExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CandidateExperienceService {

    @Autowired
    private CandidateExperienceRepository experienceRepository;

    public CandidateExperience addExperience(Long candidateId, CandidateExperienceDTO dto) {
        CandidateExperience e = mapToEntity(new CandidateExperience(), dto);
        e.setCandidateId(candidateId);
        return experienceRepository.save(e);
    }

    public List<CandidateExperience> getExperience(Long candidateId) {
        return experienceRepository.findByCandidateId(candidateId);
    }

    public CandidateExperience updateExperience(Long id, Long candidateId, CandidateExperienceDTO dto) {
        CandidateExperience existing = experienceRepository.findByIdAndCandidateId(id, candidateId)
            .orElseThrow(() -> new RuntimeException("Experience not found or doesn't belong to you"));
        return experienceRepository.save(mapToEntity(existing, dto));
    }

    public void deleteExperience(Long id, Long candidateId) {
        CandidateExperience existing = experienceRepository.findByIdAndCandidateId(id, candidateId)
            .orElseThrow(() -> new RuntimeException("Experience not found or doesn't belong to you"));
        experienceRepository.delete(existing);
    }

    private CandidateExperience mapToEntity(CandidateExperience e, CandidateExperienceDTO dto) {
        if (dto.getJobTitle() != null) e.setJobTitle(dto.getJobTitle());
        if (dto.getCompanyName() != null) e.setCompanyName(dto.getCompanyName());
        if (dto.getEmploymentType() != null) e.setEmploymentType(dto.getEmploymentType());
        if (dto.getLocation() != null) e.setLocation(dto.getLocation());
        if (dto.getIsRemote() != null) e.setIsRemote(dto.getIsRemote());
        if (dto.getStartDate() != null) e.setStartDate(dto.getStartDate());
        if (dto.getEndDate() != null) e.setEndDate(dto.getEndDate());
        if (dto.getIsCurrent() != null) e.setIsCurrent(dto.getIsCurrent());
        if (dto.getDescription() != null) e.setDescription(dto.getDescription());
        if (dto.getSkillsUsed() != null) e.setSkillsUsed(dto.getSkillsUsed());
        return e;
    }
}
