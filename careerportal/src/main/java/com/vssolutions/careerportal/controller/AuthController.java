package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.model.Candidate;
import com.vssolutions.careerportal.model.Recruiter;
import com.vssolutions.careerportal.repository.RecruiterRepository;
import com.vssolutions.careerportal.security.JwtUtil;
import com.vssolutions.careerportal.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
public ResponseEntity<?> register(@RequestBody Candidate candidate) {
    try {
        Candidate saved = candidateService.registerCandidate(candidate);
        String token = jwtUtil.generateToken(saved.getEmail(), saved.getRole());
        return ResponseEntity.ok(Map.of(
            "id",       saved.getId(),
            "fullName", saved.getFullName(),
            "email",    saved.getEmail(),
            "role",     saved.getRole(),
            "token",    token
        ));
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
    }
}
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        try {
            String email    = body.get("email");
            String password = body.get("password");

            Candidate candidate = candidateService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

            if (!passwordEncoder.matches(password, candidate.getPassword())) {
                return ResponseEntity.badRequest().body(Map.of("message", "Invalid email or password"));
            }

            String token = jwtUtil.generateToken(candidate.getEmail(), candidate.getRole());
            return ResponseEntity.ok(Map.of(
                "id",       candidate.getId(),
                "fullName", candidate.getFullName(),
                "email",    candidate.getEmail(),
                "role",     candidate.getRole(),
                "token",    token
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/recruiter/login")
    public ResponseEntity<?> recruiterLogin(@RequestBody Map<String, String> body) {
        try {
            String email    = body.get("email");
            String password = body.get("password");

            Recruiter recruiter = recruiterRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

            if (!passwordEncoder.matches(password, recruiter.getPassword())) {
                return ResponseEntity.badRequest().body(Map.of("message", "Invalid email or password"));
            }

            String token = jwtUtil.generateToken(recruiter.getEmail(), recruiter.getRole());
            return ResponseEntity.ok(Map.of(
                "id",      recruiter.getId(),
                "name",    recruiter.getName(),
                "email",   recruiter.getEmail(),
                "company", recruiter.getCompany(),
                "role",    recruiter.getRole(),
                "token",   token
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}