package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.dto.CandidateEducationDTO;
import com.vssolutions.careerportal.model.CandidateEducation;
import com.vssolutions.careerportal.repository.CandidateEducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CandidateEducationService {

    @Autowired
    private CandidateEducationRepository educationRepository;

    public CandidateEducation addEducation(Long candidateId, CandidateEducationDTO dto) {
        CandidateEducation e = mapToEntity(new CandidateEducation(), dto);
        e.setCandidateId(candidateId);
        return educationRepository.save(e);
    }

    public List<CandidateEducation> getEducation(Long candidateId) {
        return educationRepository.findByCandidateId(candidateId);
    }

    // Ownership check happens here: id + candidateId must both match
    public CandidateEducation updateEducation(Long id, Long candidateId, CandidateEducationDTO dto) {
        CandidateEducation existing = educationRepository.findByIdAndCandidateId(id, candidateId)
            .orElseThrow(() -> new RuntimeException("Education record not found or doesn't belong to you"));
        return educationRepository.save(mapToEntity(existing, dto));
    }

    public void deleteEducation(Long id, Long candidateId) {
        CandidateEducation existing = educationRepository.findByIdAndCandidateId(id, candidateId)
            .orElseThrow(() -> new RuntimeException("Education record not found or doesn't belong to you"));
        educationRepository.delete(existing);
    }

    private CandidateEducation mapToEntity(CandidateEducation e, CandidateEducationDTO dto) {
        if (dto.getDegree() != null) e.setDegree(dto.getDegree());
        if (dto.getFieldOfStudy() != null) e.setFieldOfStudy(dto.getFieldOfStudy());
        if (dto.getInstitution() != null) e.setInstitution(dto.getInstitution());
        if (dto.getBoardOrUniversity() != null) e.setBoardOrUniversity(dto.getBoardOrUniversity());
        if (dto.getStartYear() != null) e.setStartYear(dto.getStartYear());
        if (dto.getEndYear() != null) e.setEndYear(dto.getEndYear());
        if (dto.getIsPursuing() != null) e.setIsPursuing(dto.getIsPursuing());
        if (dto.getGradeOrPercentage() != null) e.setGradeOrPercentage(dto.getGradeOrPercentage());
        if (dto.getDescription() != null) e.setDescription(dto.getDescription());
        return e;
    }
}
