package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.dto.CandidateEducationDTO;
import com.vssolutions.careerportal.model.CandidateEducation;
import com.vssolutions.careerportal.model.CandidateProfile;
import com.vssolutions.careerportal.repository.CandidateProfileRepository;
import com.vssolutions.careerportal.service.CandidateEducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

// SECURITY NOTE: candidateId is NEVER taken from the URL. It is always resolved
// from the logged-in user's JWT email -> their CandidateProfile.id. This prevents
// one candidate from reading/editing another candidate's education records.
@RestController
@RequestMapping("/api/profiles/candidate/education")
public class CandidateEducationController {

    @Autowired
    private CandidateEducationService educationService;

    @Autowired
    private CandidateProfileRepository candidateProfileRepository;

    private Long resolveCandidateId(Authentication auth) {
        CandidateProfile profile = candidateProfileRepository.findByEmail(auth.getName())
            .orElseThrow(() -> new RuntimeException("Candidate profile not found - create your profile first"));
        return profile.getId();
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody CandidateEducationDTO dto, Authentication auth) {
        try {
            Long candidateId = resolveCandidateId(auth);
            CandidateEducation created = educationService.addEducation(candidateId, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(Authentication auth) {
        try {
            Long candidateId = resolveCandidateId(auth);
            return ResponseEntity.ok(educationService.getEducation(candidateId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CandidateEducationDTO dto, Authentication auth) {
        try {
            Long candidateId = resolveCandidateId(auth);
            return ResponseEntity.ok(educationService.updateEducation(id, candidateId, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Authentication auth) {
        try {
            Long candidateId = resolveCandidateId(auth);
            educationService.deleteEducation(id, candidateId);
            return ResponseEntity.ok(Map.of("message", "Education record deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }
}
