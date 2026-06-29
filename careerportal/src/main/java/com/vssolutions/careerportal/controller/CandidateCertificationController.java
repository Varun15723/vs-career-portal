package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.dto.CandidateCertificationDTO;
import com.vssolutions.careerportal.model.CandidateCertification;
import com.vssolutions.careerportal.model.CandidateProfile;
import com.vssolutions.careerportal.repository.CandidateProfileRepository;
import com.vssolutions.careerportal.service.CandidateCertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/profiles/candidate/certifications")
public class CandidateCertificationController {

    @Autowired
    private CandidateCertificationService certificationService;

    @Autowired
    private CandidateProfileRepository candidateProfileRepository;

    private Long resolveCandidateId(Authentication auth) {
        CandidateProfile profile = candidateProfileRepository.findByEmail(auth.getName())
            .orElseThrow(() -> new RuntimeException("Candidate profile not found - create your profile first"));
        return profile.getId();
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody CandidateCertificationDTO dto, Authentication auth) {
        try {
            Long candidateId = resolveCandidateId(auth);
            CandidateCertification created = certificationService.addCertification(candidateId, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(Authentication auth) {
        try {
            Long candidateId = resolveCandidateId(auth);
            return ResponseEntity.ok(certificationService.getCertifications(candidateId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CandidateCertificationDTO dto, Authentication auth) {
        try {
            Long candidateId = resolveCandidateId(auth);
            return ResponseEntity.ok(certificationService.updateCertification(id, candidateId, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Authentication auth) {
        try {
            Long candidateId = resolveCandidateId(auth);
            certificationService.deleteCertification(id, candidateId);
            return ResponseEntity.ok(Map.of("message", "Certification deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }
}
