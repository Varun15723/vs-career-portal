package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.repository.ApplicationRepository;
import com.vssolutions.careerportal.repository.JobRepository;
import com.vssolutions.careerportal.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        return ResponseEntity.ok(Map.of(
            "totalCandidates",   candidateService.getAllCandidates().size(),
            "totalJobs",         jobRepository.count(),
            "totalApplications", applicationRepository.count()
        ));
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(candidateService.getAllCandidates());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
        return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
    }
}