package com.vssolutions.careerportal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "candidate_social_links",
       uniqueConstraints = @UniqueConstraint(columnNames = {"candidate_id", "platform"}))
public class CandidateSocialLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "candidate_id", nullable = false)
    private Long candidateId;

    @Column(name = "platform", nullable = false, length = 50)
    private String platform;

    @Column(name = "url", nullable = false, length = 500)
    private String url;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCandidateId() { return candidateId; }
    public void setCandidateId(Long v) { this.candidateId = v; }
    public String getPlatform() { return platform; }
    public void setPlatform(String v) { this.platform = v; }
    public String getUrl() { return url; }
    public void setUrl(String v) { this.url = v; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
