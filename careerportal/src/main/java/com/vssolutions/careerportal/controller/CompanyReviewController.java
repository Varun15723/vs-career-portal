package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.dto.CompanyReviewRequest;
import com.vssolutions.careerportal.model.Candidate;
import com.vssolutions.careerportal.service.CandidateService;
import com.vssolutions.careerportal.service.CompanyReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/companies/{companyName}/reviews")
public class CompanyReviewController {

    @Autowired
    private CompanyReviewService companyReviewService;

    @Autowired
    private CandidateService candidateService;

    // Public - anyone can read reviews for a company
    @GetMapping
    public ResponseEntity<?> getReviews(@PathVariable String companyName) {
        return ResponseEntity.ok(Map.of(
            "summary", companyReviewService.getSummary(companyName),
            "reviews", companyReviewService.getReviews(companyName)
        ));
    }

    // Candidate only - matches "/api/candidates/**".hasRole("CANDIDATE") pattern,
    // so this route needs its own SecurityConfig rule (see PATCH_NOTES.md)
    @PostMapping
    public ResponseEntity<?> addReview(@PathVariable String companyName,
                                        @RequestBody CompanyReviewRequest request,
                                        Authentication auth) {
        try {
            Candidate candidate = candidateService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
            return ResponseEntity.ok(companyReviewService.addReview(
                candidate, companyName, request.getRating(), request.getReviewText()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
