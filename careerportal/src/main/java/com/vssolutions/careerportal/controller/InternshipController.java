package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.model.Internship;
import com.vssolutions.careerportal.model.Recruiter;
import com.vssolutions.careerportal.service.InternshipService;
import com.vssolutions.careerportal.service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/internships")
public class InternshipController {

    @Autowired
    private InternshipService internshipService;

    @Autowired
    private RecruiterService recruiterService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(internshipService.getAllInternships());
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String q) {
        return ResponseEntity.ok(internshipService.search(q));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return internshipService.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Recruiter only - mirrors JobController.createJob
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Internship internship, Authentication auth) {
        try {
            if (auth == null) {
                return ResponseEntity.status(401).body(Map.of("message", "Unauthorized - Please login"));
            }
            Recruiter recruiter = recruiterService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Recruiter not found"));
            internship.setPostedBy(recruiter);
            return ResponseEntity.ok(internshipService.createInternship(internship));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Internship internship) {
        try {
            return ResponseEntity.ok(internshipService.updateInternship(id, internship));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        internshipService.deleteInternship(id);
        return ResponseEntity.ok(Map.of("message", "Internship deleted successfully"));
    }
}
