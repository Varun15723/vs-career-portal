package com.vssolutions.careerportal.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "candidate_experience")
public class CandidateExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "candidate_id", nullable = false)
    private Long candidateId;

    @Column(name = "job_title", nullable = false, length = 150)
    private String jobTitle;

    @Column(name = "company_name", nullable = false, length = 200)
    private String companyName;

    @Column(name = "employment_type", length = 50)
    private String employmentType;

    @Column(name = "location", length = 150)
    private String location;

    @Column(name = "is_remote", nullable = false)
    private Boolean isRemote = false;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_current", nullable = false)
    private Boolean isCurrent = false;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "skills_used", columnDefinition = "TEXT")
    private String skillsUsed;

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
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String v) { this.jobTitle = v; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String v) { this.companyName = v; }
    public String getEmploymentType() { return employmentType; }
    public void setEmploymentType(String v) { this.employmentType = v; }
    public String getLocation() { return location; }
    public void setLocation(String v) { this.location = v; }
    public Boolean getIsRemote() { return isRemote; }
    public void setIsRemote(Boolean v) { this.isRemote = v; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate v) { this.startDate = v; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate v) { this.endDate = v; }
    public Boolean getIsCurrent() { return isCurrent; }
    public void setIsCurrent(Boolean v) { this.isCurrent = v; }
    public String getDescription() { return description; }
    public void setDescription(String v) { this.description = v; }
    public String getSkillsUsed() { return skillsUsed; }
    public void setSkillsUsed(String v) { this.skillsUsed = v; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
