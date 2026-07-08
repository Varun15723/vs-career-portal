package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.dto.SendOtpRequest;
import com.vssolutions.careerportal.dto.VerifyOtpRequest;
import com.vssolutions.careerportal.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// Mounted under /api/auth - already permitAll() in SecurityConfig, no changes needed.
@RestController
@RequestMapping("/api/auth/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/send")
    public ResponseEntity<?> sendOtp(@RequestBody SendOtpRequest request) {
        try {
            String result = otpService.sendOtp(request.getEmail(), request.getPurpose());
            return ResponseEntity.ok(Map.of("message", result));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest request) {
        try {
            String result = otpService.verifyOtp(request.getEmail(), request.getCode(), request.getPurpose());
            return ResponseEntity.ok(Map.of("message", result));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
