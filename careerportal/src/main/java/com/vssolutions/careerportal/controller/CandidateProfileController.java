package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.dto.CandidateProfileDTO;
import com.vssolutions.careerportal.model.CandidateProfile;
import com.vssolutions.careerportal.service.CandidateProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller exposing Candidate Profile APIs.
 *
 * Base URL: /api/profiles/candidate
 *
 * Folder: careerportal/src/main/java/com/vscareerportal/careerportal/controller/CandidateProfileController.java
 */
@RestController
@RequestMapping("/api/profiles/candidate")
@CrossOrigin(origins = "*")
public class CandidateProfileController {

    @Autowired
    private CandidateProfileService candidateProfileService;

    // ----------------------------------------------------------------
    // POST /api/profiles/candidate
    // Create a new candidate profile
    // ----------------------------------------------------------------
    @PostMapping
    public ResponseEntity<?> createProfile(@RequestBody CandidateProfileDTO dto) {
        try {
            CandidateProfile created = candidateProfileService.createProfile(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // ----------------------------------------------------------------
    // GET /api/profiles/candidate/{id}
    // Get profile by profile ID
    // ----------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable Long id) {
        return candidateProfileService.getProfileById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found"));
    }

    // ----------------------------------------------------------------
    // GET /api/profiles/candidate/user/{userId}
    // Get profile by user ID (most common lookup)
    // ----------------------------------------------------------------
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getProfileByUserId(@PathVariable Long userId) {
        return candidateProfileService.getProfileByUserId(userId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found for userId: " + userId));
    }

    // ----------------------------------------------------------------
    // GET /api/profiles/candidate
    // Get all candidate profiles
    // ----------------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<CandidateProfile>> getAllProfiles() {
        return ResponseEntity.ok(candidateProfileService.getAllProfiles());
    }

    // ----------------------------------------------------------------
    // GET /api/profiles/candidate/available
    // Get all candidates currently available for hire
    // ----------------------------------------------------------------
    @GetMapping("/available")
    public ResponseEntity<List<CandidateProfile>> getAvailableCandidates() {
        return ResponseEntity.ok(candidateProfileService.getAvailableCandidates());
    }

    // ----------------------------------------------------------------
    // GET /api/profiles/candidate/search?skill=Java&city=Mumbai&name=Rahul
    // Search candidates by skill / city / name
    // ----------------------------------------------------------------
    @GetMapping("/search")
    public ResponseEntity<List<CandidateProfile>> search(
            @RequestParam(required = false) String skill,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minExp,
            @RequestParam(required = false) Integer maxExp) {

        if (skill != null)
            return ResponseEntity.ok(candidateProfileService.searchBySkill(skill));
        if (city != null)
            return ResponseEntity.ok(candidateProfileService.searchByCity(city));
        if (name != null)
            return ResponseEntity.ok(candidateProfileService.searchByName(name));
        if (minExp != null && maxExp != null)
            return ResponseEntity.ok(candidateProfileService.searchByExperience(minExp, maxExp));

        return ResponseEntity.ok(candidateProfileService.getAllProfiles());
    }

    // ----------------------------------------------------------------
    // PUT /api/profiles/candidate/user/{userId}
    // Update profile for a given user
    // ----------------------------------------------------------------
    @PutMapping("/user/{userId}")
    public ResponseEntity<?> updateProfile(@PathVariable Long userId,
                                           @RequestBody CandidateProfileDTO dto) {
        try {
            CandidateProfile updated = candidateProfileService.updateProfile(userId, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ----------------------------------------------------------------
    // PATCH /api/profiles/candidate/user/{userId}/toggle-availability
    // Toggle job-seeking availability flag
    // ----------------------------------------------------------------
    @PatchMapping("/user/{userId}/toggle-availability")
    public ResponseEntity<?> toggleAvailability(@PathVariable Long userId) {
        try {
            CandidateProfile updated = candidateProfileService.toggleAvailability(userId);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ----------------------------------------------------------------
    // DELETE /api/profiles/candidate/user/{userId}
    // Delete a candidate profile
    // ----------------------------------------------------------------
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteProfile(@PathVariable Long userId) {
        try {
            candidateProfileService.deleteProfile(userId);
            return ResponseEntity.ok("Candidate profile deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

