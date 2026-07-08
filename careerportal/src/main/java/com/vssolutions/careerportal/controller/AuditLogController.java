package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Mounted under /api/admin - already hasRole("ADMIN") in SecurityConfig, no changes needed.
@RestController
@RequestMapping("/api/admin/audit-logs")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @GetMapping
    public ResponseEntity<?> getAllLogs() {
        return ResponseEntity.ok(auditLogService.getAllLogs());
    }

    @GetMapping("/entity/{entityType}")
    public ResponseEntity<?> getByEntityType(@PathVariable String entityType) {
        return ResponseEntity.ok(auditLogService.getLogsByEntityType(entityType));
    }

    @GetMapping("/actor/{email}")
    public ResponseEntity<?> getByActor(@PathVariable String email) {
        return ResponseEntity.ok(auditLogService.getLogsByActor(email));
    }
}
