package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.model.Job;
import com.vssolutions.careerportal.model.RecruiterProfile;
import com.vssolutions.careerportal.repository.CompanyReviewRepository;
import com.vssolutions.careerportal.repository.JobRepository;
import com.vssolutions.careerportal.repository.RecruiterProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

// NOTE: This does NOT introduce a new Company entity - it's a public, read-only
// view built on top of the RecruiterProfile data your team already collects
// (companyName, companyLogoUrl, industry, isVerified, etc.) plus active Job counts.
// This is what candidates see when "browsing companies".
@Service
public class CompanyService {

    @Autowired
    private RecruiterProfileRepository recruiterProfileRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyReviewRepository companyReviewRepository;

    public List<Map<String, Object>> browseCompanies() {
        List<RecruiterProfile> profiles = recruiterProfileRepository.findByIsVerifiedTrue();
        List<Job> allActiveJobs = jobRepository.findByIsActiveTrue();

        // group active job counts by company name (case-insensitive)
        Map<String, Long> jobCountByCompany = allActiveJobs.stream()
            .collect(Collectors.groupingBy(j -> j.getCompany().toLowerCase(), Collectors.counting()));

        List<Map<String, Object>> result = new ArrayList<>();
        for (RecruiterProfile profile : profiles) {
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("companyName", profile.getCompanyName());
            entry.put("industry", profile.getIndustry());
            entry.put("companyLogoUrl", profile.getCompanyLogoUrl());
            entry.put("city", profile.getCity());
            entry.put("country", profile.getCountry());
            entry.put("activeJobCount", jobCountByCompany.getOrDefault(profile.getCompanyName().toLowerCase(), 0L));

            Double avgRating = companyReviewRepository.getAverageRating(profile.getCompanyName());
            entry.put("averageRating", avgRating == null ? 0.0 : Math.round(avgRating * 10.0) / 10.0);

            result.add(entry);
        }
        return result;
    }

    public Map<String, Object> getCompanyDetail(String companyName) {
        RecruiterProfile profile = recruiterProfileRepository.searchByCompanyName(companyName).stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Company not found"));

        List<Job> jobs = jobRepository.findByIsActiveTrue().stream()
            .filter(j -> j.getCompany().equalsIgnoreCase(profile.getCompanyName()))
            .collect(Collectors.toList());

        Double avgRating = companyReviewRepository.getAverageRating(profile.getCompanyName());
        long reviewCount = companyReviewRepository.countByCompanyNameIgnoreCase(profile.getCompanyName());

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("companyName", profile.getCompanyName());
        result.put("description", profile.getCompanyDescription());
        result.put("website", profile.getCompanyWebsite());
        result.put("industry", profile.getIndustry());
        result.put("companySize", profile.getCompanySize());
        result.put("logoUrl", profile.getCompanyLogoUrl());
        result.put("city", profile.getCity());
        result.put("country", profile.getCountry());
        result.put("averageRating", avgRating == null ? 0.0 : Math.round(avgRating * 10.0) / 10.0);
        result.put("totalReviews", reviewCount);
        result.put("activeJobs", jobs);
        return result;
    }
}
