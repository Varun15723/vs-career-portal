package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.dto.SendMessageRequest;
import com.vssolutions.careerportal.model.Candidate;
import com.vssolutions.careerportal.model.Recruiter;
import com.vssolutions.careerportal.service.CandidateService;
import com.vssolutions.careerportal.service.MessageService;
import com.vssolutions.careerportal.service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// Covered by the existing "/api/notifications/**".authenticated()-style catch-all
// (.anyRequest().authenticated()) - works for both candidates and recruiters as-is.
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private RecruiterService recruiterService;

    // Resolves the logged-in user's id + type ("candidate"/"recruiter") from their JWT email
    private Map.Entry<Long, String> resolveCaller(Authentication auth) {
        var candidate = candidateService.findByEmail(auth.getName());
        if (candidate.isPresent()) {
            return Map.entry(candidate.get().getId(), "candidate");
        }
        Recruiter recruiter = recruiterService.findByEmail(auth.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));
        return Map.entry(recruiter.getId(), "recruiter");
    }

    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody SendMessageRequest request, Authentication auth) {
        try {
            var caller = resolveCaller(auth);
            var message = messageService.sendMessage(
                caller.getKey(), caller.getValue(), request.getReceiverId(), request.getReceiverType(), request.getContent());
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/inbox")
    public ResponseEntity<?> getInbox(Authentication auth) {
        var caller = resolveCaller(auth);
        return ResponseEntity.ok(messageService.getInbox(caller.getKey(), caller.getValue()));
    }

    @GetMapping("/conversation/{otherUserType}/{otherUserId}")
    public ResponseEntity<?> getConversation(@PathVariable String otherUserType,
                                              @PathVariable Long otherUserId,
                                              Authentication auth) {
        var caller = resolveCaller(auth);
        return ResponseEntity.ok(messageService.getConversation(
            caller.getKey(), caller.getValue(), otherUserId, otherUserType));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(messageService.markAsRead(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/unread-count")
    public ResponseEntity<?> getUnreadCount(Authentication auth) {
        var caller = resolveCaller(auth);
        return ResponseEntity.ok(Map.of("unreadCount", messageService.getUnreadCount(caller.getKey(), caller.getValue())));
    }
}
