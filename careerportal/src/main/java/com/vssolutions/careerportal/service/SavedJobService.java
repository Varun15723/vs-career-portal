package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.model.Candidate;
import com.vssolutions.careerportal.model.Job;
import com.vssolutions.careerportal.model.SavedJob;
import com.vssolutions.careerportal.repository.JobRepository;
import com.vssolutions.careerportal.repository.SavedJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SavedJobService {

    @Autowired
    private SavedJobRepository savedJobRepository;

    @Autowired
    private JobRepository jobRepository;

    // Save a job for a candidate
    public SavedJob saveJob(Candidate candidate, Long jobId) {
        if (savedJobRepository.existsByCandidateIdAndJobId(candidate.getId(), jobId)) {
            throw new RuntimeException("Job already saved");
        }

        Job job = jobRepository.findById(jobId)
            .orElseThrow(() -> new RuntimeException("Job not found"));

        SavedJob savedJob = new SavedJob();
        savedJob.setCandidate(candidate);
        savedJob.setJob(job);
        return savedJobRepository.save(savedJob);
    }

    // Get all saved jobs for a candidate
    public List<SavedJob> getSavedJobs(Long candidateId) {
        return savedJobRepository.findByCandidateId(candidateId);
    }

    // Remove a saved job
    public void unsaveJob(Long candidateId, Long jobId) {
        if (!savedJobRepository.existsByCandidateIdAndJobId(candidateId, jobId)) {
            throw new RuntimeException("Saved job not found");
        }
        savedJobRepository.deleteByCandidateIdAndJobId(candidateId, jobId);
    }
}
