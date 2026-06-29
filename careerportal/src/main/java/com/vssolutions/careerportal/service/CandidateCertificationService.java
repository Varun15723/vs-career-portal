package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.dto.CandidateCertificationDTO;
import com.vssolutions.careerportal.model.CandidateCertification;
import com.vssolutions.careerportal.repository.CandidateCertificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CandidateCertificationService {

    @Autowired
    private CandidateCertificationRepository certRepository;

    public CandidateCertification addCertification(Long candidateId, CandidateCertificationDTO dto) {
        CandidateCertification c = mapToEntity(new CandidateCertification(), dto);
        c.setCandidateId(candidateId);
        return certRepository.save(c);
    }

    public List<CandidateCertification> getCertifications(Long candidateId) {
        return certRepository.findByCandidateId(candidateId);
    }

    public CandidateCertification updateCertification(Long id, Long candidateId, CandidateCertificationDTO dto) {
        CandidateCertification existing = certRepository.findByIdAndCandidateId(id, candidateId)
            .orElseThrow(() -> new RuntimeException("Certification not found or doesn't belong to you"));
        return certRepository.save(mapToEntity(existing, dto));
    }

    public void deleteCertification(Long id, Long candidateId) {
        CandidateCertification existing = certRepository.findByIdAndCandidateId(id, candidateId)
            .orElseThrow(() -> new RuntimeException("Certification not found or doesn't belong to you"));
        certRepository.delete(existing);
    }

    private CandidateCertification mapToEntity(CandidateCertification c, CandidateCertificationDTO dto) {
        if (dto.getName() != null) c.setName(dto.getName());
        if (dto.getIssuingOrganization() != null) c.setIssuingOrganization(dto.getIssuingOrganization());
        if (dto.getCredentialId() != null) c.setCredentialId(dto.getCredentialId());
        if (dto.getCredentialUrl() != null) c.setCredentialUrl(dto.getCredentialUrl());
        if (dto.getIssueDate() != null) c.setIssueDate(dto.getIssueDate());
        if (dto.getExpiryDate() != null) c.setExpiryDate(dto.getExpiryDate());
        if (dto.getDoesNotExpire() != null) c.setDoesNotExpire(dto.getDoesNotExpire());
        return c;
    }
}
