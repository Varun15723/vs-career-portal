package com.vssolutions.careerportal.dto;

public class SendOtpRequest {
    private String email;
    private String purpose; // "REGISTRATION", "LOGIN", "EMAIL_VERIFICATION"

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
}
