package com.vssolutions.careerportal.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "candidate_certifications")
public class CandidateCertification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "candidate_id", nullable = false)
    private Long candidateId;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "issuing_organization", nullable = false, length = 200)
    private String issuingOrganization;

    @Column(name = "credential_id", length = 150)
    private String credentialId;

    @Column(name = "credential_url", length = 500)
    private String credentialUrl;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "does_not_expire", nullable = false)
    private Boolean doesNotExpire = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() { createdAt = updatedAt = LocalDateTime.now(); }

    @PreUpdate
    protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCandidateId() { return candidateId; }
    public void setCandidateId(Long v) { this.candidateId = v; }
    public String getName() { return name; }
    public void setName(String v) { this.name = v; }
    public String getIssuingOrganization() { return issuingOrganization; }
    public void setIssuingOrganization(String v) { this.issuingOrganization = v; }
    public String getCredentialId() { return credentialId; }
    public void setCredentialId(String v) { this.credentialId = v; }
    public String getCredentialUrl() { return credentialUrl; }
    public void setCredentialUrl(String v) { this.credentialUrl = v; }
    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate v) { this.issueDate = v; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate v) { this.expiryDate = v; }
    public Boolean getDoesNotExpire() { return doesNotExpire; }
    public void setDoesNotExpire(Boolean v) { this.doesNotExpire = v; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
