package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.dto.CandidateLanguageDTO;
import com.vssolutions.careerportal.model.CandidateLanguage;
import com.vssolutions.careerportal.model.CandidateProfile;
import com.vssolutions.careerportal.repository.CandidateProfileRepository;
import com.vssolutions.careerportal.service.CandidateLanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/profiles/candidate/languages")
public class CandidateLanguageController {

    @Autowired
    private CandidateLanguageService languageService;

    @Autowired
    private CandidateProfileRepository candidateProfileRepository;

    private Long resolveCandidateId(Authentication auth) {
        CandidateProfile profile = candidateProfileRepository.findByEmail(auth.getName())
            .orElseThrow(() -> new RuntimeException("Candidate profile not found - create your profile first"));
        return profile.getId();
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody CandidateLanguageDTO dto, Authentication auth) {
        try {
            Long candidateId = resolveCandidateId(auth);
            CandidateLanguage created = languageService.addLanguage(candidateId, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(Authentication auth) {
        try {
            Long candidateId = resolveCandidateId(auth);
            return ResponseEntity.ok(languageService.getLanguages(candidateId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CandidateLanguageDTO dto, Authentication auth) {
        try {
            Long candidateId = resolveCandidateId(auth);
            return ResponseEntity.ok(languageService.updateLanguage(id, candidateId, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Authentication auth) {
        try {
            Long candidateId = resolveCandidateId(auth);
            languageService.deleteLanguage(id, candidateId);
            return ResponseEntity.ok(Map.of("message", "Language removed successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }
}
