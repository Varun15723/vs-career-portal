package com.vssolutions.careerportal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a Recruiter's profile.
 * Table: recruiter_profiles
 *
 * Folder: careerportal/src/main/java/com/vscareerportal/careerportal/model/RecruiterProfile.java
 */
@Entity
@Table(name = "recruiter_profiles")
public class RecruiterProfile {

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

    @Column(name = "designation", length = 150)
    private String designation;

    // --- Company Info ---
    @Column(name = "company_name", nullable = false, length = 200)
    private String companyName;

    @Column(name = "company_website", length = 300)
    private String companyWebsite;

    @Column(name = "company_size", length = 50)
    private String companySize;  // e.g. "1-10", "50-200", "500+"

    @Column(name = "industry", length = 100)
    private String industry;

    @Column(name = "company_description", columnDefinition = "TEXT")
    private String companyDescription;

    @Column(name = "company_logo_url", length = 500)
    private String companyLogoUrl;

    // --- Location ---
    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "country", length = 100)
    private String country;

    // --- Social ---
    @Column(name = "linkedin_url", length = 300)
    private String linkedinUrl;

    // --- Status ---
    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false;

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
        int total = 8, filled = 0;
        if (fullName != null && !fullName.isBlank()) filled++;
        if (email != null && !email.isBlank()) filled++;
        if (phone != null && !phone.isBlank()) filled++;
        if (companyName != null && !companyName.isBlank()) filled++;
        if (designation != null && !designation.isBlank()) filled++;
        if (industry != null && !industry.isBlank()) filled++;
        if (companyDescription != null && !companyDescription.isBlank()) filled++;
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

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getCompanyWebsite() { return companyWebsite; }
    public void setCompanyWebsite(String companyWebsite) { this.companyWebsite = companyWebsite; }

    public String getCompanySize() { return companySize; }
    public void setCompanySize(String companySize) { this.companySize = companySize; }

    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }

    public String getCompanyDescription() { return companyDescription; }
    public void setCompanyDescription(String companyDescription) { this.companyDescription = companyDescription; }

    public String getCompanyLogoUrl() { return companyLogoUrl; }
    public void setCompanyLogoUrl(String companyLogoUrl) { this.companyLogoUrl = companyLogoUrl; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getLinkedinUrl() { return linkedinUrl; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }

    public Boolean getIsVerified() { return isVerified; }
    public void setIsVerified(Boolean isVerified) { this.isVerified = isVerified; }

    public Integer getProfileCompletionPercent() { return profileCompletionPercent; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}

