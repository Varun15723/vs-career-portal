package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.model.AuditLog;
import com.vssolutions.careerportal.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    // Fire-and-forget style log call - kept deliberately simple (no async/queue)
    // so it's easy to drop into any existing service method with one line.
    public void log(String actorEmail, String actorRole, String action,
                     String entityType, Long entityId, String details) {
        AuditLog entry = new AuditLog();
        entry.setActorEmail(actorEmail);
        entry.setActorRole(actorRole);
        entry.setAction(action);
        entry.setEntityType(entityType);
        entry.setEntityId(entityId);
        entry.setDetails(details);
        auditLogRepository.save(entry);
    }

    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAllByOrderByTimestampDesc();
    }

    public List<AuditLog> getLogsByEntityType(String entityType) {
        return auditLogRepository.findByEntityTypeOrderByTimestampDesc(entityType);
    }

    public List<AuditLog> getLogsByActor(String actorEmail) {
        return auditLogRepository.findByActorEmailOrderByTimestampDesc(actorEmail);
    }
}
