package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.dto.RecruiterProfileDTO;
import com.vssolutions.careerportal.model.RecruiterProfile;
import com.vssolutions.careerportal.repository.RecruiterProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Recruiter Profile business logic.
 *
 * Folder: careerportal/src/main/java/com/vscareerportal/careerportal/service/RecruiterProfileService.java
 */
@Service
public class RecruiterProfileService {

    @Autowired
    private RecruiterProfileRepository recruiterProfileRepository;

    // ----------------------------------------------------------------
    // CREATE
    // ----------------------------------------------------------------
    public RecruiterProfile createProfile(RecruiterProfileDTO dto) {
        if (recruiterProfileRepository.existsByUserId(dto.getUserId())) {
            throw new RuntimeException("Recruiter profile already exists for userId: " + dto.getUserId());
        }
        if (recruiterProfileRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already registered: " + dto.getEmail());
        }
        RecruiterProfile profile = mapDtoToEntity(new RecruiterProfile(), dto);
        return recruiterProfileRepository.save(profile);
    }

    // ----------------------------------------------------------------
    // READ
    // ----------------------------------------------------------------
    public Optional<RecruiterProfile> getProfileById(Long id) {
        return recruiterProfileRepository.findById(id);
    }

    public Optional<RecruiterProfile> getProfileByUserId(Long userId) {
        return recruiterProfileRepository.findByUserId(userId);
    }

    public List<RecruiterProfile> getAllProfiles() {
        return recruiterProfileRepository.findAll();
    }

    public List<RecruiterProfile> getVerifiedRecruiters() {
        return recruiterProfileRepository.findByIsVerifiedTrue();
    }

    public List<RecruiterProfile> searchByCompany(String companyName) {
        return recruiterProfileRepository.searchByCompanyName(companyName);
    }

    public List<RecruiterProfile> searchByIndustry(String industry) {
        return recruiterProfileRepository.findByIndustryIgnoreCase(industry);
    }

    public List<RecruiterProfile> searchByCity(String city) {
        return recruiterProfileRepository.findByCityIgnoreCase(city);
    }

    // ----------------------------------------------------------------
    // UPDATE
    // ----------------------------------------------------------------
    public RecruiterProfile updateProfile(Long userId, RecruiterProfileDTO dto) {
        RecruiterProfile existing = recruiterProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Recruiter profile not found for userId: " + userId));
        mapDtoToEntity(existing, dto);
        return recruiterProfileRepository.save(existing);
    }

    public RecruiterProfile verifyRecruiter(Long userId) {
        RecruiterProfile profile = recruiterProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Recruiter profile not found for userId: " + userId));
        profile.setIsVerified(true);
        return recruiterProfileRepository.save(profile);
    }

    // ----------------------------------------------------------------
    // DELETE
    // ----------------------------------------------------------------
    public void deleteProfile(Long userId) {
        RecruiterProfile profile = recruiterProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Recruiter profile not found for userId: " + userId));
        recruiterProfileRepository.delete(profile);
    }

    // ----------------------------------------------------------------
    // HELPER
    // ----------------------------------------------------------------
    private RecruiterProfile mapDtoToEntity(RecruiterProfile profile, RecruiterProfileDTO dto) {
        if (dto.getUserId() != null)             profile.setUserId(dto.getUserId());
        if (dto.getFullName() != null)           profile.setFullName(dto.getFullName());
        if (dto.getEmail() != null)              profile.setEmail(dto.getEmail());
        if (dto.getPhone() != null)              profile.setPhone(dto.getPhone());
        if (dto.getDesignation() != null)        profile.setDesignation(dto.getDesignation());
        if (dto.getCompanyName() != null)        profile.setCompanyName(dto.getCompanyName());
        if (dto.getCompanyWebsite() != null)     profile.setCompanyWebsite(dto.getCompanyWebsite());
        if (dto.getCompanySize() != null)        profile.setCompanySize(dto.getCompanySize());
        if (dto.getIndustry() != null)           profile.setIndustry(dto.getIndustry());
        if (dto.getCompanyDescription() != null) profile.setCompanyDescription(dto.getCompanyDescription());
        if (dto.getCompanyLogoUrl() != null)     profile.setCompanyLogoUrl(dto.getCompanyLogoUrl());
        if (dto.getCity() != null)               profile.setCity(dto.getCity());
        if (dto.getState() != null)              profile.setState(dto.getState());
        if (dto.getCountry() != null)            profile.setCountry(dto.getCountry());
        if (dto.getLinkedinUrl() != null)        profile.setLinkedinUrl(dto.getLinkedinUrl());
        return profile;
    }
}

