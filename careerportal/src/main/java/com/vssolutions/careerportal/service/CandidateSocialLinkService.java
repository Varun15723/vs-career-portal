package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.dto.CandidateSocialLinkDTO;
import com.vssolutions.careerportal.model.CandidateSocialLink;
import com.vssolutions.careerportal.repository.CandidateSocialLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CandidateSocialLinkService {

    @Autowired
    private CandidateSocialLinkRepository socialLinkRepository;

    public CandidateSocialLink addSocialLink(Long candidateId, CandidateSocialLinkDTO dto) {
        if (dto.getPlatform() == null || dto.getPlatform().isBlank()) {
            throw new RuntimeException("Platform is required");
        }
        if (socialLinkRepository.existsByCandidateIdAndPlatformIgnoreCase(candidateId, dto.getPlatform())) {
            throw new RuntimeException("Link for this platform already exists: " + dto.getPlatform());
        }
        CandidateSocialLink s = mapToEntity(new CandidateSocialLink(), dto);
        s.setCandidateId(candidateId);
        return socialLinkRepository.save(s);
    }

    public List<CandidateSocialLink> getSocialLinks(Long candidateId) {
        return socialLinkRepository.findByCandidateId(candidateId);
    }

    public CandidateSocialLink updateSocialLink(Long id, Long candidateId, CandidateSocialLinkDTO dto) {
        CandidateSocialLink existing = socialLinkRepository.findByIdAndCandidateId(id, candidateId)
            .orElseThrow(() -> new RuntimeException("Social link not found or doesn't belong to you"));
        return socialLinkRepository.save(mapToEntity(existing, dto));
    }

    public void deleteSocialLink(Long id, Long candidateId) {
        CandidateSocialLink existing = socialLinkRepository.findByIdAndCandidateId(id, candidateId)
            .orElseThrow(() -> new RuntimeException("Social link not found or doesn't belong to you"));
        socialLinkRepository.delete(existing);
    }

    private CandidateSocialLink mapToEntity(CandidateSocialLink s, CandidateSocialLinkDTO dto) {
        if (dto.getPlatform() != null) s.setPlatform(dto.getPlatform());
        if (dto.getUrl() != null) s.setUrl(dto.getUrl());
        return s;
    }
}
