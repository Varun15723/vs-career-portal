package com.vssolutions.careerportal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recruiter_team_members",
       uniqueConstraints = @UniqueConstraint(columnNames = {"recruiter_profile_id", "email"}))
public class RecruiterTeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // References RecruiterProfile.id of the OWNING company account (the inviter)
    @Column(name = "recruiter_profile_id", nullable = false)
    private Long recruiterProfileId;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    // Identifies the invited teammate - matched against the Recruiter login table by email
    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "designation", length = 150)
    private String designation;

    // ADMIN | MEMBER | VIEWER
    @Column(name = "role", nullable = false, length = 30)
    private String role = "MEMBER";

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "invited_at", updatable = false)
    private LocalDateTime invitedAt;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    @PrePersist
    protected void onCreate() { invitedAt = LocalDateTime.now(); }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getRecruiterProfileId() { return recruiterProfileId; }
    public void setRecruiterProfileId(Long v) { this.recruiterProfileId = v; }
    public String getFullName() { return fullName; }
    public void setFullName(String v) { this.fullName = v; }
    public String getEmail() { return email; }
    public void setEmail(String v) { this.email = v; }
    public String getDesignation() { return designation; }
    public void setDesignation(String v) { this.designation = v; }
    public String getRole() { return role; }
    public void setRole(String v) { this.role = v; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean v) { this.isActive = v; }
    public LocalDateTime getInvitedAt() { return invitedAt; }
    public LocalDateTime getJoinedAt() { return joinedAt; }
    public void setJoinedAt(LocalDateTime v) { this.joinedAt = v; }
}
