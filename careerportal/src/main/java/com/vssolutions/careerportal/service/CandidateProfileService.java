package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.dto.CandidateProfileDTO;
import com.vssolutions.careerportal.model.CandidateProfile;
import com.vssolutions.careerportal.repository.CandidateProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Candidate Profile business logic.
 *
 * Folder: careerportal/src/main/java/com/vscareerportal/careerportal/service/CandidateProfileService.java
 */
@Service
public class CandidateProfileService {

    @Autowired
    private CandidateProfileRepository candidateProfileRepository;

    // ----------------------------------------------------------------
    // CREATE
    // ----------------------------------------------------------------
    public CandidateProfile createProfile(CandidateProfileDTO dto) {
        if (candidateProfileRepository.existsByUserId(dto.getUserId())) {
            throw new RuntimeException("Candidate profile already exists for userId: " + dto.getUserId());
        }
        if (candidateProfileRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already registered: " + dto.getEmail());
        }
        CandidateProfile profile = mapDtoToEntity(new CandidateProfile(), dto);
        return candidateProfileRepository.save(profile);
    }

    // ----------------------------------------------------------------
    // READ
    // ----------------------------------------------------------------
    public Optional<CandidateProfile> getProfileById(Long id) {
        return candidateProfileRepository.findById(id);
    }

    public Optional<CandidateProfile> getProfileByUserId(Long userId) {
        return candidateProfileRepository.findByUserId(userId);
    }

    public List<CandidateProfile> getAllProfiles() {
        return candidateProfileRepository.findAll();
    }

    public List<CandidateProfile> getAvailableCandidates() {
        return candidateProfileRepository.findByIsAvailableTrue();
    }

    public List<CandidateProfile> searchBySkill(String skill) {
        return candidateProfileRepository.findBySkill(skill);
    }

    public List<CandidateProfile> searchByName(String name) {
        return candidateProfileRepository.searchByName(name);
    }

    public List<CandidateProfile> searchByCity(String city) {
        return candidateProfileRepository.findByCityIgnoreCase(city);
    }

    public List<CandidateProfile> searchByExperience(int min, int max) {
        return candidateProfileRepository.findByExperienceRange(min, max);
    }

    // ----------------------------------------------------------------
    // UPDATE
    // ----------------------------------------------------------------
    public CandidateProfile updateProfile(Long userId, CandidateProfileDTO dto) {
        CandidateProfile existing = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Candidate profile not found for userId: " + userId));
        mapDtoToEntity(existing, dto);
        return candidateProfileRepository.save(existing);
    }

    public CandidateProfile toggleAvailability(Long userId) {
        CandidateProfile profile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Candidate profile not found for userId: " + userId));
        profile.setIsAvailable(!profile.getIsAvailable());
        return candidateProfileRepository.save(profile);
    }

    // ----------------------------------------------------------------
    // DELETE
    // ----------------------------------------------------------------
    public void deleteProfile(Long userId) {
        CandidateProfile profile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Candidate profile not found for userId: " + userId));
        candidateProfileRepository.delete(profile);
    }

    // ----------------------------------------------------------------
    // HELPER
    // ----------------------------------------------------------------
    private CandidateProfile mapDtoToEntity(CandidateProfile profile, CandidateProfileDTO dto) {
        if (dto.getUserId() != null)             profile.setUserId(dto.getUserId());
        if (dto.getFullName() != null)           profile.setFullName(dto.getFullName());
        if (dto.getEmail() != null)              profile.setEmail(dto.getEmail());
        if (dto.getPhone() != null)              profile.setPhone(dto.getPhone());
        if (dto.getDateOfBirth() != null)        profile.setDateOfBirth(dto.getDateOfBirth());
        if (dto.getGender() != null)             profile.setGender(dto.getGender());
        if (dto.getAddress() != null)            profile.setAddress(dto.getAddress());
        if (dto.getCity() != null)               profile.setCity(dto.getCity());
        if (dto.getState() != null)              profile.setState(dto.getState());
        if (dto.getCountry() != null)            profile.setCountry(dto.getCountry());
        if (dto.getPincode() != null)            profile.setPincode(dto.getPincode());
        if (dto.getHeadline() != null)           profile.setHeadline(dto.getHeadline());
        if (dto.getSummary() != null)            profile.setSummary(dto.getSummary());
        if (dto.getSkills() != null)             profile.setSkills(dto.getSkills());
        if (dto.getExperienceYears() != null)    profile.setExperienceYears(dto.getExperienceYears());
        if (dto.getCurrentCompany() != null)     profile.setCurrentCompany(dto.getCurrentCompany());
        if (dto.getCurrentDesignation() != null) profile.setCurrentDesignation(dto.getCurrentDesignation());
        if (dto.getNoticePeriodDays() != null)   profile.setNoticePeriodDays(dto.getNoticePeriodDays());
        if (dto.getExpectedSalary() != null)     profile.setExpectedSalary(dto.getExpectedSalary());
        if (dto.getHighestQualification() != null) profile.setHighestQualification(dto.getHighestQualification());
        if (dto.getInstitution() != null)        profile.setInstitution(dto.getInstitution());
        if (dto.getGraduationYear() != null)     profile.setGraduationYear(dto.getGraduationYear());
        if (dto.getResumeUrl() != null)          profile.setResumeUrl(dto.getResumeUrl());
        if (dto.getProfilePictureUrl() != null)  profile.setProfilePictureUrl(dto.getProfilePictureUrl());
        if (dto.getLinkedinUrl() != null)        profile.setLinkedinUrl(dto.getLinkedinUrl());
        if (dto.getGithubUrl() != null)          profile.setGithubUrl(dto.getGithubUrl());
        if (dto.getPortfolioUrl() != null)       profile.setPortfolioUrl(dto.getPortfolioUrl());
        if (dto.getIsAvailable() != null)        profile.setIsAvailable(dto.getIsAvailable());
        return profile;
    }
}

