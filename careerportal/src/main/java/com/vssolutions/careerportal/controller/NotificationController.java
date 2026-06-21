package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.model.Candidate;
import com.vssolutions.careerportal.model.Notification;
import com.vssolutions.careerportal.service.CandidateService;
import com.vssolutions.careerportal.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private CandidateService candidateService;

    // GET /api/notifications - returns all notifications for the logged-in user
    @GetMapping
    public ResponseEntity<?> getNotifications(Authentication auth) {
        try {
            Candidate candidate = candidateService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
            List<Notification> notifications = notificationService.getNotifications(candidate.getId());
            return ResponseEntity.ok(notifications);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // PUT /api/notifications/{id}/read - marks a single notification as read
    @PutMapping("/{id}/read")
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        try {
            Notification notification = notificationService.markAsRead(id);
            return ResponseEntity.ok(notification);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
