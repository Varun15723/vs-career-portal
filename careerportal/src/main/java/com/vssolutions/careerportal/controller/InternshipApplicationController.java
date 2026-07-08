package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.model.Candidate;
import com.vssolutions.careerportal.service.CandidateService;
import com.vssolutions.careerportal.service.InternshipApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/internship-applications")
public class InternshipApplicationController {

    @Autowired
    private InternshipApplicationService internshipApplicationService;

    @Autowired
    private CandidateService candidateService;

    @PostMapping("/apply/{internshipId}")
    public ResponseEntity<?> apply(@PathVariable Long internshipId, Authentication auth) {
        try {
            Candidate candidate = candidateService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
            return ResponseEntity.ok(internshipApplicationService.apply(candidate, internshipId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/my")
    public ResponseEntity<?> myApplications(Authentication auth) {
        Candidate candidate = candidateService.findByEmail(auth.getName())
            .orElseThrow(() -> new RuntimeException("Candidate not found"));
        return ResponseEntity.ok(internshipApplicationService.getMyApplications(candidate.getId()));
    }

    @GetMapping("/internship/{internshipId}")
    public ResponseEntity<?> getForInternship(@PathVariable Long internshipId) {
        return ResponseEntity.ok(internshipApplicationService.getApplicationsForInternship(internshipId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            return ResponseEntity.ok(internshipApplicationService.updateStatus(id, body.get("status")));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
