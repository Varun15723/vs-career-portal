package com.vssolutions.careerportal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "candidate_resume_versions")
public class CandidateResumeVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "candidate_id", nullable = false)
    private Long candidateId;

    @Column(name = "version_label", nullable = false, length = 100)
    private String versionLabel;

    @Column(name = "file_url", nullable = false, length = 500)
    private String fileUrl;

    @Column(name = "file_name", length = 255)
    private String fileName;

    @Column(name = "file_size_kb")
    private Integer fileSizeKb;

    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary = false;

    // Reserved for a future AI resume-scoring feature - not set by this module.
    @Column(name = "ai_score")
    private Integer aiScore;

    @Column(name = "ai_feedback", columnDefinition = "TEXT")
    private String aiFeedback;

    @Column(name = "uploaded_at", updatable = false)
    private LocalDateTime uploadedAt;

    @PrePersist
    protected void onCreate() { uploadedAt = LocalDateTime.now(); }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCandidateId() { return candidateId; }
    public void setCandidateId(Long v) { this.candidateId = v; }
    public String getVersionLabel() { return versionLabel; }
    public void setVersionLabel(String v) { this.versionLabel = v; }
    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String v) { this.fileUrl = v; }
    public String getFileName() { return fileName; }
    public void setFileName(String v) { this.fileName = v; }
    public Integer getFileSizeKb() { return fileSizeKb; }
    public void setFileSizeKb(Integer v) { this.fileSizeKb = v; }
    public Boolean getIsPrimary() { return isPrimary; }
    public void setIsPrimary(Boolean v) { this.isPrimary = v; }
    public Integer getAiScore() { return aiScore; }
    public void setAiScore(Integer v) { this.aiScore = v; }
    public String getAiFeedback() { return aiFeedback; }
    public void setAiFeedback(String v) { this.aiFeedback = v; }
    public LocalDateTime getUploadedAt() { return uploadedAt; }
}
