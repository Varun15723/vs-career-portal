package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// Public browsing - no auth required. Add "/api/companies/**".permitAll() to
// SecurityConfig (it currently falls through to .anyRequest().authenticated(),
// see PATCH_NOTES.md).
@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<?> browseCompanies() {
        return ResponseEntity.ok(companyService.browseCompanies());
    }

    @GetMapping("/{companyName}")
    public ResponseEntity<?> getCompanyDetail(@PathVariable String companyName) {
        try {
            return ResponseEntity.ok(companyService.getCompanyDetail(companyName));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
