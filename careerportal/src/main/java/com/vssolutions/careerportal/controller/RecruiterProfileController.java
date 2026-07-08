package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.dto.RecruiterProfileDTO;
import com.vssolutions.careerportal.model.RecruiterProfile;
import com.vssolutions.careerportal.service.RecruiterProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller exposing Recruiter Profile APIs.
 *
 * Base URL: /api/profiles/recruiter
 *
 * Folder: careerportal/src/main/java/com/vscareerportal/careerportal/controller/RecruiterProfileController.java
 */
@RestController
@RequestMapping("/api/profiles/recruiter")
@CrossOrigin(origins = "*")
public class RecruiterProfileController {

    @Autowired
    private RecruiterProfileService recruiterProfileService;

    // ----------------------------------------------------------------
    // POST /api/profiles/recruiter
    // Create a new recruiter profile
    // ----------------------------------------------------------------
    @PostMapping
    public ResponseEntity<?> createProfile(@RequestBody RecruiterProfileDTO dto) {
        try {
            RecruiterProfile created = recruiterProfileService.createProfile(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // ----------------------------------------------------------------
    // GET /api/profiles/recruiter/{id}
    // Get profile by profile ID
    // ----------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable Long id) {
        return recruiterProfileService.getProfileById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found"));
    }

    // ----------------------------------------------------------------
    // GET /api/profiles/recruiter/user/{userId}
    // Get profile by user ID
    // ----------------------------------------------------------------
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getProfileByUserId(@PathVariable Long userId) {
        return recruiterProfileService.getProfileByUserId(userId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found for userId: " + userId));
    }

    // ----------------------------------------------------------------
    // GET /api/profiles/recruiter
    // Get all recruiter profiles
    // ----------------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<RecruiterProfile>> getAllProfiles() {
        return ResponseEntity.ok(recruiterProfileService.getAllProfiles());
    }

    // ----------------------------------------------------------------
    // GET /api/profiles/recruiter/verified
    // Get all verified recruiters
    // ----------------------------------------------------------------
    @GetMapping("/verified")
    public ResponseEntity<List<RecruiterProfile>> getVerifiedRecruiters() {
        return ResponseEntity.ok(recruiterProfileService.getVerifiedRecruiters());
    }

    // ----------------------------------------------------------------
    // GET /api/profiles/recruiter/search?company=TCS&industry=IT&city=Pune
    // Search recruiters by company / industry / city
    // ----------------------------------------------------------------
    @GetMapping("/search")
    public ResponseEntity<List<RecruiterProfile>> search(
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String industry,
            @RequestParam(required = false) String city) {

        if (company != null)
            return ResponseEntity.ok(recruiterProfileService.searchByCompany(company));
        if (industry != null)
            return ResponseEntity.ok(recruiterProfileService.searchByIndustry(industry));
        if (city != null)
            return ResponseEntity.ok(recruiterProfileService.searchByCity(city));

        return ResponseEntity.ok(recruiterProfileService.getAllProfiles());
    }

    // ----------------------------------------------------------------
    // PUT /api/profiles/recruiter/user/{userId}
    // Update recruiter profile
    // ----------------------------------------------------------------
    @PutMapping("/user/{userId}")
    public ResponseEntity<?> updateProfile(@PathVariable Long userId,
                                           @RequestBody RecruiterProfileDTO dto) {
        try {
            RecruiterProfile updated = recruiterProfileService.updateProfile(userId, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ----------------------------------------------------------------
    // PATCH /api/profiles/recruiter/user/{userId}/verify
    // Admin verifies a recruiter (admin-only in a secured setup)
    // ----------------------------------------------------------------
    @PatchMapping("/user/{userId}/verify")
    public ResponseEntity<?> verifyRecruiter(@PathVariable Long userId) {
        try {
            RecruiterProfile updated = recruiterProfileService.verifyRecruiter(userId);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ----------------------------------------------------------------
    // DELETE /api/profiles/recruiter/user/{userId}
    // Delete a recruiter profile
    // ----------------------------------------------------------------
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteProfile(@PathVariable Long userId) {
        try {
            recruiterProfileService.deleteProfile(userId);
            return ResponseEntity.ok("Recruiter profile deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

