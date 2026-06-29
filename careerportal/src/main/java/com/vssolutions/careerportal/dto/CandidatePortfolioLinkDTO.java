package com.vssolutions.careerportal.dto;

public class CandidatePortfolioLinkDTO {
    private String title;
    private String url;
    private String description;
    private String technologyStack;
    private String linkType;

    public String getTitle() { return title; }
    public void setTitle(String v) { this.title = v; }
    public String getUrl() { return url; }
    public void setUrl(String v) { this.url = v; }
    public String getDescription() { return description; }
    public void setDescription(String v) { this.description = v; }
    public String getTechnologyStack() { return technologyStack; }
    public void setTechnologyStack(String v) { this.technologyStack = v; }
    public String getLinkType() { return linkType; }
    public void setLinkType(String v) { this.linkType = v; }
}
