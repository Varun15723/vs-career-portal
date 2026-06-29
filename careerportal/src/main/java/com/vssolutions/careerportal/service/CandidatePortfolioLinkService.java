package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.dto.CandidatePortfolioLinkDTO;
import com.vssolutions.careerportal.model.CandidatePortfolioLink;
import com.vssolutions.careerportal.repository.CandidatePortfolioLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CandidatePortfolioLinkService {

    @Autowired
    private CandidatePortfolioLinkRepository portfolioRepository;

    public CandidatePortfolioLink addLink(Long candidateId, CandidatePortfolioLinkDTO dto) {
        CandidatePortfolioLink p = mapToEntity(new CandidatePortfolioLink(), dto);
        p.setCandidateId(candidateId);
        return portfolioRepository.save(p);
    }

    public List<CandidatePortfolioLink> getLinks(Long candidateId) {
        return portfolioRepository.findByCandidateId(candidateId);
    }

    public CandidatePortfolioLink updateLink(Long id, Long candidateId, CandidatePortfolioLinkDTO dto) {
        CandidatePortfolioLink existing = portfolioRepository.findByIdAndCandidateId(id, candidateId)
            .orElseThrow(() -> new RuntimeException("Portfolio link not found or doesn't belong to you"));
        return portfolioRepository.save(mapToEntity(existing, dto));
    }

    public void deleteLink(Long id, Long candidateId) {
        CandidatePortfolioLink existing = portfolioRepository.findByIdAndCandidateId(id, candidateId)
            .orElseThrow(() -> new RuntimeException("Portfolio link not found or doesn't belong to you"));
        portfolioRepository.delete(existing);
    }

    private CandidatePortfolioLink mapToEntity(CandidatePortfolioLink p, CandidatePortfolioLinkDTO dto) {
        if (dto.getTitle() != null) p.setTitle(dto.getTitle());
        if (dto.getUrl() != null) p.setUrl(dto.getUrl());
        if (dto.getDescription() != null) p.setDescription(dto.getDescription());
        if (dto.getTechnologyStack() != null) p.setTechnologyStack(dto.getTechnologyStack());
        if (dto.getLinkType() != null) p.setLinkType(dto.getLinkType());
        return p;
    }
}
