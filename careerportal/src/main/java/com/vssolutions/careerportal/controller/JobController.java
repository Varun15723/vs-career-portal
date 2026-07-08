package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.model.Job;
import com.vssolutions.careerportal.model.Recruiter;
import com.vssolutions.careerportal.service.AuditLogService;
import com.vssolutions.careerportal.service.JobService;
import com.vssolutions.careerportal.service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private RecruiterService recruiterService;

    // NEW — Audit Logs module
    @Autowired
    private AuditLogService auditLogService;

    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/search")
    public List<Job> searchJobs(@RequestParam String q) {
        return jobService.searchJobs(q);
    }

    // Filter jobs by location, jobType, experience, department.
    // All params optional - e.g. GET /api/jobs/filter?location=Hyderabad&jobType=Full-time
    @GetMapping("/filter")
    public List<Job> filterJobs(@RequestParam(required = false) String location,
                                 @RequestParam(required = false) String jobType,
                                 @RequestParam(required = false) String experience,
                                 @RequestParam(required = false) String department) {
        return jobService.filterJobs(location, jobType, experience, department);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJobById(@PathVariable Long id) {
        return jobService.getJobById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createJob(@RequestBody Job job, Authentication auth) {
        try {
            if (auth == null) {
                return ResponseEntity.status(401).body(Map.of("message", "Unauthorized - Please login"));
            }
            Recruiter recruiter = recruiterService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Recruiter not found"));
            job.setPostedBy(recruiter);
            Job saved = jobService.createJob(job);

            auditLogService.log(auth.getName(), "recruiter", "JOB_CREATED",
                "Job", saved.getId(), "Created job: " + saved.getTitle() + " at " + saved.getCompany());

            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJob(@PathVariable Long id, @RequestBody Job job, Authentication auth) {
        try {
            Job updated = jobService.updateJob(id, job);

            String actor = auth != null ? auth.getName() : "unknown";
            auditLogService.log(actor, "recruiter", "JOB_UPDATED",
                "Job", updated.getId(), "Updated job: " + updated.getTitle());

            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id, Authentication auth) {
        jobService.deleteJob(id);

        String actor = auth != null ? auth.getName() : "unknown";
        auditLogService.log(actor, "recruiter", "JOB_DELETED", "Job", id, "Deleted job id " + id);

        return ResponseEntity.ok(Map.of("message", "Job deleted successfully"));
    }
}
