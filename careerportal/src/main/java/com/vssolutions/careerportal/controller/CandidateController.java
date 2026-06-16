package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.model.Candidate;
import com.vssolutions.careerportal.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication auth) {
        return candidateService.findByEmail(auth.getName())
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody Candidate updatedData,
                                           Authentication auth) {
        try {
            Candidate candidate = candidateService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
            Candidate updated = candidateService.updateProfile(candidate.getId(), updatedData);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}