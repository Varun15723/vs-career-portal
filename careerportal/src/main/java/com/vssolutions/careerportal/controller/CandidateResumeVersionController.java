package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.model.CandidateProfile;
import com.vssolutions.careerportal.model.CandidateResumeVersion;
import com.vssolutions.careerportal.repository.CandidateProfileRepository;
import com.vssolutions.careerportal.service.CandidateResumeVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@RestController
@RequestMapping("/api/profiles/candidate/resume-versions")
public class CandidateResumeVersionController {

    @Autowired
    private CandidateResumeVersionService resumeVersionService;

    @Autowired
    private CandidateProfileRepository candidateProfileRepository;

    private Long resolveCandidateId(Authentication auth) {
        CandidateProfile profile = candidateProfileRepository.findByEmail(auth.getName())
            .orElseThrow(() -> new RuntimeException("Candidate profile not found - create your profile first"));
        return profile.getId();
    }

    // multipart/form-data - same pattern as the main /api/resume/upload endpoint
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file,
                                     @RequestParam("versionLabel") String versionLabel,
                                     @RequestParam(value = "isPrimary", defaultValue = "false") Boolean isPrimary,
                                     Authentication auth) {
        try {
            Long candidateId = resolveCandidateId(auth);
            CandidateResumeVersion created = resumeVersionService.uploadVersion(candidateId, versionLabel, isPrimary, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(Authentication auth) {
        try {
            Long candidateId = resolveCandidateId(auth);
            return ResponseEntity.ok(resumeVersionService.getVersions(candidateId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/set-primary")
    public ResponseEntity<?> setPrimary(@PathVariable Long id, Authentication auth) {
        try {
            Long candidateId = resolveCandidateId(auth);
            return ResponseEntity.ok(resumeVersionService.setPrimaryVersion(id, candidateId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Authentication auth) {
        try {
            Long candidateId = resolveCandidateId(auth);
            resumeVersionService.deleteVersion(id, candidateId);
            return ResponseEntity.ok(Map.of("message", "Resume version deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }
}
