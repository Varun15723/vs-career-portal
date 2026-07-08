package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Mounted under /api/admin so it's covered by the existing
// .requestMatchers("/api/admin/**").hasRole("ADMIN") rule in SecurityConfig - no config changes needed.
@RestController
@RequestMapping("/api/admin/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    // GET /api/admin/analytics/overview
    // Returns candidate/recruiter/job/application counts, hire rate, breakdowns by
    // status/department/location, top jobs by applications, and applications per recruiter.
    @GetMapping("/overview")
    public ResponseEntity<?> getOverview() {
        return ResponseEntity.ok(analyticsService.getDashboardOverview());
    }
}
