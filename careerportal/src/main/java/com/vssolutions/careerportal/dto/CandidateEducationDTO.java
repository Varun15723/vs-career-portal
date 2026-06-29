package com.vssolutions.careerportal.dto;

public class CandidateEducationDTO {
    private String degree;
    private String fieldOfStudy;
    private String institution;
    private String boardOrUniversity;
    private Integer startYear;
    private Integer endYear;
    private Boolean isPursuing;
    private String gradeOrPercentage;
    private String description;

    public String getDegree() { return degree; }
    public void setDegree(String v) { this.degree = v; }
    public String getFieldOfStudy() { return fieldOfStudy; }
    public void setFieldOfStudy(String v) { this.fieldOfStudy = v; }
    public String getInstitution() { return institution; }
    public void setInstitution(String v) { this.institution = v; }
    public String getBoardOrUniversity() { return boardOrUniversity; }
    public void setBoardOrUniversity(String v) { this.boardOrUniversity = v; }
    public Integer getStartYear() { return startYear; }
    public void setStartYear(Integer v) { this.startYear = v; }
    public Integer getEndYear() { return endYear; }
    public void setEndYear(Integer v) { this.endYear = v; }
    public Boolean getIsPursuing() { return isPursuing; }
    public void setIsPursuing(Boolean v) { this.isPursuing = v; }
    public String getGradeOrPercentage() { return gradeOrPercentage; }
    public void setGradeOrPercentage(String v) { this.gradeOrPercentage = v; }
    public String getDescription() { return description; }
    public void setDescription(String v) { this.description = v; }
}
