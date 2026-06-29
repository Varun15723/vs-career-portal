package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.dto.CandidateLanguageDTO;
import com.vssolutions.careerportal.model.CandidateLanguage;
import com.vssolutions.careerportal.repository.CandidateLanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CandidateLanguageService {

    @Autowired
    private CandidateLanguageRepository languageRepository;

    public CandidateLanguage addLanguage(Long candidateId, CandidateLanguageDTO dto) {
        if (dto.getLanguage() == null || dto.getLanguage().isBlank()) {
            throw new RuntimeException("Language is required");
        }
        if (languageRepository.existsByCandidateIdAndLanguageIgnoreCase(candidateId, dto.getLanguage())) {
            throw new RuntimeException("Language already added: " + dto.getLanguage());
        }
        CandidateLanguage l = mapToEntity(new CandidateLanguage(), dto);
        l.setCandidateId(candidateId);
        return languageRepository.save(l);
    }

    public List<CandidateLanguage> getLanguages(Long candidateId) {
        return languageRepository.findByCandidateId(candidateId);
    }

    public CandidateLanguage updateLanguage(Long id, Long candidateId, CandidateLanguageDTO dto) {
        CandidateLanguage existing = languageRepository.findByIdAndCandidateId(id, candidateId)
            .orElseThrow(() -> new RuntimeException("Language record not found or doesn't belong to you"));
        return languageRepository.save(mapToEntity(existing, dto));
    }

    public void deleteLanguage(Long id, Long candidateId) {
        CandidateLanguage existing = languageRepository.findByIdAndCandidateId(id, candidateId)
            .orElseThrow(() -> new RuntimeException("Language record not found or doesn't belong to you"));
        languageRepository.delete(existing);
    }

    private CandidateLanguage mapToEntity(CandidateLanguage l, CandidateLanguageDTO dto) {
        if (dto.getLanguage() != null) l.setLanguage(dto.getLanguage());
        if (dto.getProficiency() != null) l.setProficiency(dto.getProficiency());
        return l;
    }
}
