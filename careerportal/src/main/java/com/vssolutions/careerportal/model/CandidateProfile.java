package com.vssolutions.careerportal.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity representing a Candidate's profile.
 * Table: candidate_profiles
 *
 * Folder: careerportal/src/main/java/com/vscareerportal/careerportal/model/CandidateProfile.java
 */
@Entity
@Table(name = "candidate_profiles")
public class CandidateProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Basic Info ---
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "gender", length = 20)
    private String gender;

    // --- Address ---
    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "pincode", length = 10)
    private String pincode;

    // --- Professional Info ---
    @Column(name = "headline", length = 200)
    private String headline;

    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    @Column(name = "skills", columnDefinition = "TEXT")
    private String skills;   // comma-separated or JSON array stored as text

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "current_company", length = 150)
    private String currentCompany;

    @Column(name = "current_designation", length = 150)
    private String currentDesignation;

    @Column(name = "notice_period_days")
    private Integer noticePeriodDays;

    @Column(name = "expected_salary")
    private Double expectedSalary;

    // --- Education ---
    @Column(name = "highest_qualification", length = 100)
    private String highestQualification;

    @Column(name = "institution", length = 200)
    private String institution;

    @Column(name = "graduation_year")
    private Integer graduationYear;

    // --- Resume / Profile Links ---
    @Column(name = "resume_url", length = 500)
    private String resumeUrl;

    @Column(name = "profile_picture_url", length = 500)
    private String profilePictureUrl;

    @Column(name = "linkedin_url", length = 300)
    private String linkedinUrl;

    @Column(name = "github_url", length = 300)
    private String githubUrl;

    @Column(name = "portfolio_url", length = 300)
    private String portfolioUrl;

    // --- Status ---
    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = true;

    @Column(name = "profile_completion_percent")
    private Integer profileCompletionPercent = 0;

    // --- Timestamps ---
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        calculateCompletionPercent();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        calculateCompletionPercent();
    }

    private void calculateCompletionPercent() {
        int total = 10, filled = 0;
        if (fullName != null && !fullName.isBlank()) filled++;
        if (email != null && !email.isBlank()) filled++;
        if (phone != null && !phone.isBlank()) filled++;
        if (headline != null && !headline.isBlank()) filled++;
        if (summary != null && !summary.isBlank()) filled++;
        if (skills != null && !skills.isBlank()) filled++;
        if (resumeUrl != null && !resumeUrl.isBlank()) filled++;
        if (experienceYears != null) filled++;
        if (highestQualification != null && !highestQualification.isBlank()) filled++;
        if (city != null && !city.isBlank()) filled++;
        this.profileCompletionPercent = (filled * 100) / total;
    }

    // ---- Getters & Setters ----

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public String getHeadline() { return headline; }
    public void setHeadline(String headline) { this.headline = headline; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }

    public String getCurrentCompany() { return currentCompany; }
    public void setCurrentCompany(String currentCompany) { this.currentCompany = currentCompany; }

    public String getCurrentDesignation() { return currentDesignation; }
    public void setCurrentDesignation(String currentDesignation) { this.currentDesignation = currentDesignation; }

    public Integer getNoticePeriodDays() { return noticePeriodDays; }
    public void setNoticePeriodDays(Integer noticePeriodDays) { this.noticePeriodDays = noticePeriodDays; }

    public Double getExpectedSalary() { return expectedSalary; }
    public void setExpectedSalary(Double expectedSalary) { this.expectedSalary = expectedSalary; }

    public String getHighestQualification() { return highestQualification; }
    public void setHighestQualification(String highestQualification) { this.highestQualification = highestQualification; }

    public String getInstitution() { return institution; }
    public void setInstitution(String institution) { this.institution = institution; }

    public Integer getGraduationYear() { return graduationYear; }
    public void setGraduationYear(Integer graduationYear) { this.graduationYear = graduationYear; }

    public String getResumeUrl() { return resumeUrl; }
    public void setResumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; }

    public String getProfilePictureUrl() { return profilePictureUrl; }
    public void setProfilePictureUrl(String profilePictureUrl) { this.profilePictureUrl = profilePictureUrl; }

    public String getLinkedinUrl() { return linkedinUrl; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }

    public String getGithubUrl() { return githubUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }

    public String getPortfolioUrl() { return portfolioUrl; }
    public void setPortfolioUrl(String portfolioUrl) { this.portfolioUrl = portfolioUrl; }

    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }

    public Integer getProfileCompletionPercent() { return profileCompletionPercent; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}

