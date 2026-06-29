package com.vssolutions.careerportal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "candidate_languages",
       uniqueConstraints = @UniqueConstraint(columnNames = {"candidate_id", "language"}))
public class CandidateLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "candidate_id", nullable = false)
    private Long candidateId;

    @Column(name = "language", nullable = false, length = 80)
    private String language;

    @Column(name = "proficiency", nullable = false, length = 30)
    private String proficiency;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCandidateId() { return candidateId; }
    public void setCandidateId(Long v) { this.candidateId = v; }
    public String getLanguage() { return language; }
    public void setLanguage(String v) { this.language = v; }
    public String getProficiency() { return proficiency; }
    public void setProficiency(String v) { this.proficiency = v; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
