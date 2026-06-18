package com.vssolutions.careerportal.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    // Use this for all successful responses
    // Example: return ResponseUtil.success("Job created successfully", job);
    public static ResponseEntity<?> success(String message, Object data) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", "success");
        body.put("message", message);
        body.put("data", data);
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(body);
    }

    // Use this when something is created (201 instead of 200)
    // Example: return ResponseUtil.created("Application submitted", application);
    public static ResponseEntity<?> created(String message, Object data) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", "success");
        body.put("message", message);
        body.put("data", data);
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    // Use this for error responses with a specific status code
    // Example: return ResponseUtil.error("Job not found", HttpStatus.NOT_FOUND);
    public static ResponseEntity<?> error(String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", "error");
        body.put("message", message);
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(status).body(body);
    }
}
