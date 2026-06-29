package com.vssolutions.careerportal.dto;

public class RecruiterTeamMemberDTO {
    private String fullName;
    private String email;
    private String designation;
    private String role; // ADMIN | MEMBER | VIEWER

    public String getFullName() { return fullName; }
    public void setFullName(String v) { this.fullName = v; }
    public String getEmail() { return email; }
    public void setEmail(String v) { this.email = v; }
    public String getDesignation() { return designation; }
    public void setDesignation(String v) { this.designation = v; }
    public String getRole() { return role; }
    public void setRole(String v) { this.role = v; }
}
