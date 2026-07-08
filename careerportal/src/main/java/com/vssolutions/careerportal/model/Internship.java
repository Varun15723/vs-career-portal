package com.vssolutions.careerportal.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "internships")
public class Internship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String company;

    private String department;

    @Column(nullable = false)
    private String location; // can be "Remote"

    @Column(nullable = false)
    private Integer durationMonths;

    @Column(nullable = false)
    private Boolean isPaid = false;

    private String stipend; // e.g. "10000/month" - free text like Job.salary

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    private List<String> requirements;

    private String startDate; // free text, matches Job's style of storing dates as strings

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
    public Integer getDurationMonths() { return durationMonths; }
    public void setDurationMonths(Integer durationMonths) { this.durationMonths = durationMonths; }
    public Boolean getIsPaid() { return isPaid; }
    public void setIsPaid(Boolean isPaid) { this.isPaid = isPaid; }
    public String getStipend() { return stipend; }
    public void setStipend(String stipend) { this.stipend = stipend; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<String> getRequirements() { return requirements; }
    public void setRequirements(List<String> requirements) { this.requirements = requirements; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public Recruiter getPostedBy() { return postedBy; }
    public void setPostedBy(Recruiter postedBy) { this.postedBy = postedBy; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
