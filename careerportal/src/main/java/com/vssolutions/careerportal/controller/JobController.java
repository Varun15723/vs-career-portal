package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.model.Job;
import com.vssolutions.careerportal.model.Recruiter;
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

    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/search")
    public List<Job> searchJobs(@RequestParam String q) {
        return jobService.searchJobs(q);
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
            return ResponseEntity.ok(jobService.createJob(job));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJob(@PathVariable Long id, @RequestBody Job job) {
        try {
            return ResponseEntity.ok(jobService.updateJob(id, job));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.ok(Map.of("message", "Job deleted successfully"));
    }
}
