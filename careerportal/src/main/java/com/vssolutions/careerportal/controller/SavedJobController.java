package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.model.Candidate;
import com.vssolutions.careerportal.model.SavedJob;
import com.vssolutions.careerportal.service.CandidateService;
import com.vssolutions.careerportal.service.SavedJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/saved-jobs")
public class SavedJobController {

    @Autowired
    private SavedJobService savedJobService;

    @Autowired
    private CandidateService candidateService;

    // POST /api/saved-jobs/{jobId} - save a job for the logged-in candidate
    @PostMapping("/{jobId}")
    public ResponseEntity<?> saveJob(@PathVariable Long jobId, Authentication auth) {
        try {
            Candidate candidate = candidateService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
            SavedJob savedJob = savedJobService.saveJob(candidate, jobId);
            return ResponseEntity.ok(savedJob);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // GET /api/saved-jobs - returns all saved jobs for the logged-in candidate
    @GetMapping
    public ResponseEntity<?> getSavedJobs(Authentication auth) {
        try {
            Candidate candidate = candidateService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
            List<SavedJob> savedJobs = savedJobService.getSavedJobs(candidate.getId());
            return ResponseEntity.ok(savedJobs);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // DELETE /api/saved-jobs/{jobId} - remove a saved job
    @DeleteMapping("/{jobId}")
    public ResponseEntity<?> unsaveJob(@PathVariable Long jobId, Authentication auth) {
        try {
            Candidate candidate = candidateService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
            savedJobService.unsaveJob(candidate.getId(), jobId);
            return ResponseEntity.ok(Map.of("message", "Job removed from saved list"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
