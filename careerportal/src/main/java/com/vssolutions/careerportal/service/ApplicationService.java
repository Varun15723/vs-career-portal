package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.model.Application;
import com.vssolutions.careerportal.model.Candidate;
import com.vssolutions.careerportal.model.Job;
import com.vssolutions.careerportal.model.Notification;
import com.vssolutions.careerportal.repository.ApplicationRepository;
import com.vssolutions.careerportal.repository.CandidateRepository;
import com.vssolutions.careerportal.repository.JobRepository;
import com.vssolutions.careerportal.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EmailService emailService;

    // Apply for Job
    public Application applyJob(Long candidateId, Long jobId) {
        Candidate candidate = candidateRepository.findById(candidateId)
            .orElseThrow(() -> new RuntimeException("Candidate not found"));

        Job job = jobRepository.findById(jobId)
            .orElseThrow(() -> new RuntimeException("Job not found"));

        applicationRepository.findByCandidateIdAndJobId(candidateId, jobId)
            .ifPresent(a -> { throw new RuntimeException("Already applied for this job"); });

        Application application = new Application();
        application.setCandidate(candidate);
        application.setJob(job);
        Application saved = applicationRepository.save(application);

        emailService.sendApplicationEmail(
            candidate.getEmail(),
            candidate.getFullName(),
            job.getTitle(),
            job.getCompany()
        );

        Notification notification = new Notification();
        notification.setUserId(candidateId);
        notification.setMessage("Your application for " + job.getTitle() +
            " at " + job.getCompany() + " has been submitted.");
        notificationRepository.save(notification);

        return saved;
    }

    // Get Applied Jobs
    public List<Application> getAppliedJobs(Long candidateId) {
        return applicationRepository.findByCandidateId(candidateId);
    }

    // Get Applications by Job
    public List<Application> getApplicationsByJob(Long jobId) {
        return applicationRepository.findByJobId(jobId);
    }

    // Update Status
    public Application updateStatus(Long applicationId, String status) {
        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setStatus(status);
        Application saved = applicationRepository.save(application);

        emailService.sendStatusUpdateEmail(
            application.getCandidate().getEmail(),
            application.getCandidate().getFullName(),
            application.getJob().getTitle(),
            status
        );

        Notification notification = new Notification();
        notification.setUserId(application.getCandidate().getId());
        notification.setMessage("Your application for " + application.getJob().getTitle() +
            " status updated to: " + status);
        notificationRepository.save(notification);

        return saved;
    }

    // Schedule Interview
    public Application scheduleInterview(Long applicationId, String date,
                                         String time, String mode, String link) {
        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setStatus("Interview Scheduled");
        application.setInterviewDate(date);
        application.setInterviewTime(time);
        application.setInterviewMode(mode);
        application.setInterviewLink(link);
        Application saved = applicationRepository.save(application);

        emailService.sendInterviewEmail(
            application.getCandidate().getEmail(),
            application.getCandidate().getFullName(),
            application.getJob().getTitle(),
            date, time, mode, link
        );

        Notification notification = new Notification();
        notification.setUserId(application.getCandidate().getId());
        notification.setMessage("Interview scheduled for " +
            application.getJob().getTitle() + " on " + date + " at " + time);
        notificationRepository.save(notification);

        return saved;
    }
}