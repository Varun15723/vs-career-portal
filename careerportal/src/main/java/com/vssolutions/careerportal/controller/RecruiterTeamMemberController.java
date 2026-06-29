package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.dto.RecruiterTeamMemberDTO;
import com.vssolutions.careerportal.model.RecruiterProfile;
import com.vssolutions.careerportal.model.RecruiterTeamMember;
import com.vssolutions.careerportal.repository.RecruiterProfileRepository;
import com.vssolutions.careerportal.service.RecruiterTeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

// SECURITY NOTE: recruiterProfileId is always resolved from the logged-in recruiter's
// JWT email, never from the URL - so one recruiter can never manage another company's team.
@RestController
@RequestMapping("/api/profiles/recruiter/team")
public class RecruiterTeamMemberController {

    @Autowired
    private RecruiterTeamMemberService teamMemberService;

    @Autowired
    private RecruiterProfileRepository recruiterProfileRepository;

    private Long resolveRecruiterProfileId(Authentication auth) {
        RecruiterProfile profile = recruiterProfileRepository.findByEmail(auth.getName())
            .orElseThrow(() -> new RuntimeException("Recruiter profile not found - create your company profile first"));
        return profile.getId();
    }

    @PostMapping
    public ResponseEntity<?> inviteMember(@RequestBody RecruiterTeamMemberDTO dto, Authentication auth) {
        try {
            Long recruiterProfileId = resolveRecruiterProfileId(auth);
            RecruiterTeamMember created = teamMemberService.inviteMember(recruiterProfileId, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getActiveMembers(Authentication auth) {
        try {
            Long recruiterProfileId = resolveRecruiterProfileId(auth);
            return ResponseEntity.ok(teamMemberService.getActiveTeamMembers(recruiterProfileId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllMembers(Authentication auth) {
        try {
            Long recruiterProfileId = resolveRecruiterProfileId(auth);
            return ResponseEntity.ok(teamMemberService.getAllTeamMembers(recruiterProfileId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestParam String role, Authentication auth) {
        try {
            Long recruiterProfileId = resolveRecruiterProfileId(auth);
            return ResponseEntity.ok(teamMemberService.updateMemberRole(id, recruiterProfileId, role));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/join")
    public ResponseEntity<?> confirmJoin(@PathVariable Long id, Authentication auth) {
        try {
            Long recruiterProfileId = resolveRecruiterProfileId(auth);
            return ResponseEntity.ok(teamMemberService.confirmJoin(id, recruiterProfileId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeMember(@PathVariable Long id, Authentication auth) {
        try {
            Long recruiterProfileId = resolveRecruiterProfileId(auth);
            teamMemberService.removeMember(id, recruiterProfileId);
            return ResponseEntity.ok(Map.of("message", "Team member removed successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }
}
