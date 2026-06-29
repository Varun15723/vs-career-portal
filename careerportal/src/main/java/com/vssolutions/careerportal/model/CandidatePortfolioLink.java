package com.vssolutions.careerportal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "candidate_portfolio_links")
public class CandidatePortfolioLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "candidate_id", nullable = false)
    private Long candidateId;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "url", nullable = false, length = 500)
    private String url;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "technology_stack", length = 300)
    private String technologyStack;

    @Column(name = "link_type", length = 50)
    private String linkType;

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
    public String getTitle() { return title; }
    public void setTitle(String v) { this.title = v; }
    public String getUrl() { return url; }
    public void setUrl(String v) { this.url = v; }
    public String getDescription() { return description; }
    public void setDescription(String v) { this.description = v; }
    public String getTechnologyStack() { return technologyStack; }
    public void setTechnologyStack(String v) { this.technologyStack = v; }
    public String getLinkType() { return linkType; }
    public void setLinkType(String v) { this.linkType = v; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
