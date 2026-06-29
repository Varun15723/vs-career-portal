package com.vssolutions.careerportal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "candidate_education")
public class CandidateEducation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "candidate_id", nullable = false)
    private Long candidateId;

    @Column(name = "degree", nullable = false, length = 150)
    private String degree;

    @Column(name = "field_of_study", length = 150)
    private String fieldOfStudy;

    @Column(name = "institution", nullable = false, length = 250)
    private String institution;

    @Column(name = "board_or_university", length = 200)
    private String boardOrUniversity;

    @Column(name = "start_year")
    private Integer startYear;

    @Column(name = "end_year")
    private Integer endYear;

    @Column(name = "is_pursuing", nullable = false)
    private Boolean isPursuing = false;

    @Column(name = "grade_or_percentage", length = 20)
    private String gradeOrPercentage;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

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
    public String getDegree() { return degree; }
    public void setDegree(String v) { this.degree = v; }
    public String getFieldOfStudy() { return fieldOfStudy; }
    public void setFieldOfStudy(String v) { this.fieldOfStudy = v; }
    public String getInstitution() { return institution; }
    public void setInstitution(String v) { this.institution = v; }
    public String getBoardOrUniversity() { return boardOrUniversity; }
    public void setBoardOrUniversity(String v) { this.boardOrUniversity = v; }
    public Integer getStartYear() { return startYear; }
    public void setStartYear(Integer v) { this.startYear = v; }
    public Integer getEndYear() { return endYear; }
    public void setEndYear(Integer v) { this.endYear = v; }
    public Boolean getIsPursuing() { return isPursuing; }
    public void setIsPursuing(Boolean v) { this.isPursuing = v; }
    public String getGradeOrPercentage() { return gradeOrPercentage; }
    public void setGradeOrPercentage(String v) { this.gradeOrPercentage = v; }
    public String getDescription() { return description; }
    public void setDescription(String v) { this.description = v; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
