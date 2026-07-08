package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.repository.ApplicationRepository;
import com.vssolutions.careerportal.repository.CandidateRepository;
import com.vssolutions.careerportal.repository.JobRepository;
import com.vssolutions.careerportal.repository.RecruiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnalyticsService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    public Map<String, Object> getDashboardOverview() {
        Map<String, Object> result = new LinkedHashMap<>();

        long totalCandidates = candidateRepository.count();
        long totalRecruiters = recruiterRepository.count();
        long totalJobs = jobRepository.count();
        long activeJobs = jobRepository.countByIsActiveTrue();
        long totalApplications = applicationRepository.count();
        long selected = applicationRepository.countByStatusIgnoreCase("Selected");

        result.put("totalCandidates", totalCandidates);
        result.put("totalRecruiters", totalRecruiters);
        result.put("totalJobs", totalJobs);
        result.put("activeJobs", activeJobs);
        result.put("totalApplications", totalApplications);

        double hireRate = totalApplications == 0 ? 0.0
            : Math.round((selected * 10000.0) / totalApplications) / 100.0; // 2 decimal places
        result.put("hireRatePercent", hireRate);

        result.put("applicationsByStatus", toStringLongMap(applicationRepository.countByStatus()));
        result.put("jobsByDepartment", toStringLongMap(jobRepository.countByDepartment()));
        result.put("jobsByLocation", toStringLongMap(jobRepository.countByLocation()));
        result.put("topJobsByApplications", topJobs());
        result.put("applicationsByRecruiter", applicationsByRecruiter());

        return result;
    }

    // Converts [["Applied", 12], ["Selected", 3]] -> {"Applied": 12, "Selected": 3}
    private Map<String, Long> toStringLongMap(List<Object[]> rows) {
        Map<String, Long> map = new LinkedHashMap<>();
        for (Object[] row : rows) {
            String key = row[0] == null ? "Unspecified" : row[0].toString();
            Long count = ((Number) row[1]).longValue();
            map.put(key, count);
        }
        return map;
    }

    private List<Map<String, Object>> topJobs() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Object[] row : applicationRepository.countByJobOrderedDesc()) {
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("jobTitle", row[0]);
            entry.put("company", row[1]);
            entry.put("applications", ((Number) row[2]).longValue());
            list.add(entry);
        }
        return list;
    }

    private List<Map<String, Object>> applicationsByRecruiter() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Object[] row : applicationRepository.countByRecruiter()) {
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("recruiterName", row[0]);
            entry.put("company", row[1]);
            entry.put("applications", ((Number) row[2]).longValue());
            list.add(entry);
        }
        return list;
    }
}
