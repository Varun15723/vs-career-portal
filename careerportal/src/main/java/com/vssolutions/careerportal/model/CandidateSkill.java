package com.vssolutions.careerportal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "candidate_skills",
       uniqueConstraints = @UniqueConstraint(columnNames = {"candidate_id", "skill_name"}))
public class CandidateSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "candidate_id", nullable = false)
    private Long candidateId;

    @Column(name = "skill_name", nullable = false, length = 100)
    private String skillName;

    @Column(name = "proficiency_level", length = 20)
    private String proficiencyLevel;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    @Column(name = "category", length = 80)
    private String category;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCandidateId() { return candidateId; }
    public void setCandidateId(Long v) { this.candidateId = v; }
    public String getSkillName() { return skillName; }
    public void setSkillName(String v) { this.skillName = v; }
    public String getProficiencyLevel() { return proficiencyLevel; }
    public void setProficiencyLevel(String v) { this.proficiencyLevel = v; }
    public Integer getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(Integer v) { this.yearsOfExperience = v; }
    public String getCategory() { return category; }
    public void setCategory(String v) { this.category = v; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
