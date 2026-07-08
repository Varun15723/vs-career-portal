package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Mounted under /api/admin - already hasRole("ADMIN") in SecurityConfig, no changes needed.
@RestController
@RequestMapping("/api/admin/reports")
public class ReportsController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/applications-per-job")
    public ResponseEntity<?> applicationsPerJob() {
        return ResponseEntity.ok(reportService.applicationsPerJobReport());
    }

    @GetMapping("/recruiter-activity")
    public ResponseEntity<?> recruiterActivity() {
        return ResponseEntity.ok(reportService.recruiterActivityReport());
    }

    @GetMapping("/candidate-activity")
    public ResponseEntity<?> candidateActivity() {
        return ResponseEntity.ok(reportService.candidateActivityReport());
    }

    @GetMapping("/hire-rate")
    public ResponseEntity<?> hireRate() {
        return ResponseEntity.ok(reportService.hireRateReport());
    }
}
