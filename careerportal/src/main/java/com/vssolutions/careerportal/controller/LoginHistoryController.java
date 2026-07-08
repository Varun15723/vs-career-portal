package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.service.LoginHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/login-history")
public class LoginHistoryController {

    @Autowired
    private LoginHistoryService loginHistoryService;

    // Logged-in user views their own login history (candidate or recruiter)
    @GetMapping("/me")
    public ResponseEntity<?> myHistory(Authentication auth) {
        return ResponseEntity.ok(loginHistoryService.getHistoryForUser(auth.getName()));
    }

    // Admin only - mirrors "/api/admin/**".hasRole("ADMIN"); mount under /api/admin
    // instead if you want the existing rule to cover it automatically (see PATCH_NOTES.md)
    @GetMapping("/all")
    public ResponseEntity<?> allHistory() {
        return ResponseEntity.ok(loginHistoryService.getAllHistory());
    }
}
