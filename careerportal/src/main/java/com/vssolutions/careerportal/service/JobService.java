package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.model.Job;
import com.vssolutions.careerportal.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    // Get All Active Jobs
    public List<Job> getAllJobs() {
        return jobRepository.findByIsActiveTrue();
    }

    // Get Job By ID
    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }

    // Search Jobs
    public List<Job> searchJobs(String query) {
        return jobRepository
            .findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCase(query, query);
    }

    // NEW — Filter Jobs by location / jobType / experience / department
    // Pass null for any filter you don't want applied
    public List<Job> filterJobs(String location, String jobType, String experience, String department) {
        return jobRepository.filterJobs(location, jobType, experience, department);
    }

    // Create Job
    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    // Update Job
    public Job updateJob(Long id, Job updatedData) {
        Job job = jobRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Job not found"));

        if (updatedData.getTitle() != null)            job.setTitle(updatedData.getTitle());
        if (updatedData.getCompany() != null)          job.setCompany(updatedData.getCompany());
        if (updatedData.getLocation() != null)         job.setLocation(updatedData.getLocation());
        if (updatedData.getDescription() != null)      job.setDescription(updatedData.getDescription());
        if (updatedData.getJobType() != null)          job.setJobType(updatedData.getJobType());
        if (updatedData.getSalary() != null)           job.setSalary(updatedData.getSalary());
        if (updatedData.getExperience() != null)       job.setExperience(updatedData.getExperience());
        if (updatedData.getRequirements() != null)     job.setRequirements(updatedData.getRequirements());
        if (updatedData.getResponsibilities() != null) job.setResponsibilities(updatedData.getResponsibilities());

        return jobRepository.save(job);
    }

    // Delete Job
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    // Get Recruiter Jobs
    public List<Job> getJobsByRecruiter(Long recruiterId) {
        return jobRepository.findByPostedById(recruiterId);
    }
}
