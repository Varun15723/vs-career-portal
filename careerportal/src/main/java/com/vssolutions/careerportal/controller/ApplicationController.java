package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.model.Application;
import com.vssolutions.careerportal.model.Candidate;
import com.vssolutions.careerportal.service.ApplicationService;
import com.vssolutions.careerportal.service.AuditLogService;
import com.vssolutions.careerportal.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private CandidateService candidateService;

    // NEW — Audit Logs module
    @Autowired
    private AuditLogService auditLogService;

    @PostMapping("/apply/{jobId}")
    public ResponseEntity<?> applyJob(@PathVariable Long jobId, Authentication auth) {
        try {
            Candidate candidate = candidateService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
            Application application = applicationService.applyJob(candidate.getId(), jobId);
            return ResponseEntity.ok(application);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/my")
    public ResponseEntity<?> getAppliedJobs(Authentication auth) {
        Candidate candidate = candidateService.findByEmail(auth.getName())
            .orElseThrow(() -> new RuntimeException("Candidate not found"));
        List<Application> applications = applicationService.getAppliedJobs(candidate.getId());
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<?> getApplicationsByJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(applicationService.getApplicationsByJob(jobId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id,
                                          @RequestBody Map<String, String> body,
                                          Authentication auth) {
        try {
            Application application = applicationService.updateStatus(id, body.get("status"));

            String actor = auth != null ? auth.getName() : "unknown";
            auditLogService.log(actor, "recruiter", "APPLICATION_STATUS_CHANGED",
                "Application", application.getId(), "Status changed to: " + body.get("status"));

            return ResponseEntity.ok(application);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}/interview")
    public ResponseEntity<?> scheduleInterview(@PathVariable Long id,
                                               @RequestBody Map<String, String> body,
                                               Authentication auth) {
        try {
            Application application = applicationService.scheduleInterview(
                id,
                body.get("date"),
                body.get("time"),
                body.get("mode"),
                body.get("link")
            );

            String actor = auth != null ? auth.getName() : "unknown";
            auditLogService.log(actor, "recruiter", "INTERVIEW_SCHEDULED",
                "Application", application.getId(),
                "Interview on " + body.get("date") + " at " + body.get("time"));

            return ResponseEntity.ok(application);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
