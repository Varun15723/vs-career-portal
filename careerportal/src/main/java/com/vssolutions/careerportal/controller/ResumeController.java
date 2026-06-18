package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.model.Candidate;
import com.vssolutions.careerportal.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    @Autowired
    private CandidateService candidateService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // Upload Resume
    @PostMapping("/upload")
    public ResponseEntity<?> uploadResume(@RequestParam("resume") MultipartFile file,
                                          Authentication auth) {
        try {
            // File validation
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();

            if (!extension.equals(".pdf") && !extension.equals(".doc") && !extension.equals(".docx")) {
                return ResponseEntity.badRequest().body(Map.of("message", "Only PDF, DOC, DOCX files allowed"));
            }

            if (file.getSize() > 5 * 1024 * 1024) {
                return ResponseEntity.badRequest().body(Map.of("message", "File size must be less than 5MB"));
            }

            // Create upload directory
            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }

            // Generate unique filename
            String fileName = UUID.randomUUID().toString() + extension;
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, file.getBytes());

            // Update candidate resume URL
            Candidate candidate = candidateService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

            String resumeUrl = "/uploads/resumes/" + fileName;
            candidateService.updateResumeUrl(candidate.getId(), resumeUrl);

            return ResponseEntity.ok(Map.of(
                "message",   "Resume uploaded successfully",
                "resumeUrl", resumeUrl,
                "fileName",  originalFilename,
                "fileSize",  file.getSize()
            ));

        } catch (IOException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "File upload failed: " + e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // Get Resume
    @GetMapping("/my")
    public ResponseEntity<?> getResume(Authentication auth) {
        try {
            Candidate candidate = candidateService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

            if (candidate.getResumeUrl() == null) {
                return ResponseEntity.badRequest().body(Map.of("message", "No resume uploaded yet"));
            }

            return ResponseEntity.ok(Map.of("resumeUrl", candidate.getResumeUrl()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}