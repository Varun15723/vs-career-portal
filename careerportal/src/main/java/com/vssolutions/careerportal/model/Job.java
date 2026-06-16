package com.vssolutions.careerportal.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String company;

    private String department;

    @Column(nullable = false)
    private String location;

    private String experience;
    private String jobType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    private List<String> requirements;

    @ElementCollection
    private List<String> responsibilities;

    private String salary;

    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    private Recruiter postedBy;

    private Boolean isActive = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }
    public String getJobType() { return jobType; }
    public void setJobType(String jobType) { this.jobType = jobType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<String> getRequirements() { return requirements; }
    public void setRequirements(List<String> requirements) { this.requirements = requirements; }
    public List<String> getResponsibilities() { return responsibilities; }
    public void setResponsibilities(List<String> responsibilities) { this.responsibilities = responsibilities; }
    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }
    public Recruiter getPostedBy() { return postedBy; }
    public void setPostedBy(Recruiter postedBy) { this.postedBy = postedBy; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}