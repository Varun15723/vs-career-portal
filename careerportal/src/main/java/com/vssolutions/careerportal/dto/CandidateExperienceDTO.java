package com.vssolutions.careerportal.dto;

import java.time.LocalDate;

public class CandidateExperienceDTO {
    private String jobTitle;
    private String companyName;
    private String employmentType;
    private String location;
    private Boolean isRemote;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isCurrent;
    private String description;
    private String skillsUsed;

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
}
