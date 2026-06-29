package com.vssolutions.careerportal.dto;

import java.time.LocalDate;

public class CandidateCertificationDTO {
    private String name;
    private String issuingOrganization;
    private String credentialId;
    private String credentialUrl;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private Boolean doesNotExpire;

    public String getName() { return name; }
    public void setName(String v) { this.name = v; }
    public String getIssuingOrganization() { return issuingOrganization; }
    public void setIssuingOrganization(String v) { this.issuingOrganization = v; }
    public String getCredentialId() { return credentialId; }
    public void setCredentialId(String v) { this.credentialId = v; }
    public String getCredentialUrl() { return credentialUrl; }
    public void setCredentialUrl(String v) { this.credentialUrl = v; }
    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate v) { this.issueDate = v; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate v) { this.expiryDate = v; }
    public Boolean getDoesNotExpire() { return doesNotExpire; }
    public void setDoesNotExpire(Boolean v) { this.doesNotExpire = v; }
}
