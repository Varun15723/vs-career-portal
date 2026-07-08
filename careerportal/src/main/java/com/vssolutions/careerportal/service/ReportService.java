package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.model.Candidate;
import com.vssolutions.careerportal.repository.ApplicationRepository;
import com.vssolutions.careerportal.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

// Reuses the analytics query methods already added to ApplicationRepository/JobRepository
// (see AnalyticsService) but formats them as exportable, per-entity report rows rather
// than a single aggregate dashboard - e.g. "which candidates are most active" instead of
// just "how many applications total".
@Service
public class ReportService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    // Jobs ranked by number of applications received
    public List<Map<String, Object>> applicationsPerJobReport() {
        List<Map<String, Object>> report = new ArrayList<>();
        for (Object[] row : applicationRepository.countByJobOrderedDesc()) {
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("jobTitle", row[0]);
            entry.put("company", row[1]);
            entry.put("totalApplications", ((Number) row[2]).longValue());
            report.add(entry);
        }
        return report;
    }

    // Recruiter/company activity - jobs' applications grouped by recruiter
    public List<Map<String, Object>> recruiterActivityReport() {
        List<Map<String, Object>> report = new ArrayList<>();
        for (Object[] row : applicationRepository.countByRecruiter()) {
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("recruiterName", row[0]);
            entry.put("company", row[1]);
            entry.put("totalApplicationsReceived", ((Number) row[2]).longValue());
            report.add(entry);
        }
        return report;
    }

    // Candidate activity - how many applications each candidate has submitted
    public List<Map<String, Object>> candidateActivityReport() {
        List<Map<String, Object>> report = new ArrayList<>();
        for (Candidate candidate : candidateRepository.findAll()) {
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("candidateId", candidate.getId());
            entry.put("fullName", candidate.getFullName());
            entry.put("email", candidate.getEmail());
            entry.put("totalApplications", applicationRepository.findByCandidateId(candidate.getId()).size());
            report.add(entry);
        }
        return report;
    }

    // Hire rate as its own standalone report row (also available via Dashboard Analytics overview)
    public Map<String, Object> hireRateReport() {
        long total = applicationRepository.count();
        long selected = applicationRepository.countByStatusIgnoreCase("Selected");
        double rate = total == 0 ? 0.0 : Math.round((selected * 10000.0) / total) / 100.0;

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalApplications", total);
        result.put("totalSelected", selected);
        result.put("hireRatePercent", rate);
        return result;
    }
}
