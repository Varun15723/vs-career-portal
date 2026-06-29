package com.vssolutions.careerportal.dto;

public class CandidateSkillDTO {
    private String skillName;
    private String proficiencyLevel;
    private Integer yearsOfExperience;
    private String category;

    public String getSkillName() { return skillName; }
    public void setSkillName(String v) { this.skillName = v; }
    public String getProficiencyLevel() { return proficiencyLevel; }
    public void setProficiencyLevel(String v) { this.proficiencyLevel = v; }
    public Integer getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(Integer v) { this.yearsOfExperience = v; }
    public String getCategory() { return category; }
    public void setCategory(String v) { this.category = v; }
}
