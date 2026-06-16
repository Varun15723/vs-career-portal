package com.vssolutions.careerportal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Column(nullable = false)
    private String status = "Applied";

    private String interviewDate;
    private String interviewTime;
    private String interviewMode;
    private String interviewLink;

    private LocalDateTime appliedDate = LocalDateTime.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Candidate getCandidate() { return candidate; }
    public void setCandidate(Candidate candidate) { this.candidate = candidate; }
    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getInterviewDate() { return interviewDate; }
    public void setInterviewDate(String interviewDate) { this.interviewDate = interviewDate; }
    public String getInterviewTime() { return interviewTime; }
    public void setInterviewTime(String interviewTime) { this.interviewTime = interviewTime; }
    public String getInterviewMode() { return interviewMode; }
    public void setInterviewMode(String interviewMode) { this.interviewMode = interviewMode; }
    public String getInterviewLink() { return interviewLink; }
    public void setInterviewLink(String interviewLink) { this.interviewLink = interviewLink; }
    public LocalDateTime getAppliedDate() { return appliedDate; }
    public void setAppliedDate(LocalDateTime appliedDate) { this.appliedDate = appliedDate; }
}