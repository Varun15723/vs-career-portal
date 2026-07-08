package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.model.Candidate;
import com.vssolutions.careerportal.model.Internship;
import com.vssolutions.careerportal.model.InternshipApplication;
import com.vssolutions.careerportal.repository.InternshipApplicationRepository;
import com.vssolutions.careerportal.repository.InternshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternshipApplicationService {

    @Autowired
    private InternshipApplicationRepository internshipApplicationRepository;

    @Autowired
    private InternshipRepository internshipRepository;

    // Reuses the existing generic application-received email (jobTitle param doubles as internship title)
    @Autowired
    private EmailService emailService;

    public InternshipApplication apply(Candidate candidate, Long internshipId) {
        Internship internship = internshipRepository.findById(internshipId)
            .orElseThrow(() -> new RuntimeException("Internship not found"));

        internshipApplicationRepository.findByCandidateIdAndInternshipId(candidate.getId(), internshipId)
            .ifPresent(a -> { throw new RuntimeException("Already applied for this internship"); });

        InternshipApplication application = new InternshipApplication();
        application.setCandidate(candidate);
        application.setInternship(internship);
        InternshipApplication saved = internshipApplicationRepository.save(application);

        emailService.sendApplicationEmail(
            candidate.getEmail(), candidate.getFullName(), internship.getTitle(), internship.getCompany()
        );

        return saved;
    }

    public List<InternshipApplication> getMyApplications(Long candidateId) {
        return internshipApplicationRepository.findByCandidateId(candidateId);
    }

    public List<InternshipApplication> getApplicationsForInternship(Long internshipId) {
        return internshipApplicationRepository.findByInternshipId(internshipId);
    }

    public InternshipApplication updateStatus(Long applicationId, String status) {
        InternshipApplication application = internshipApplicationRepository.findById(applicationId)
            .orElseThrow(() -> new RuntimeException("Application not found"));
        application.setStatus(status);
        InternshipApplication saved = internshipApplicationRepository.save(application);

        emailService.sendStatusUpdateEmail(
            application.getCandidate().getEmail(),
            application.getCandidate().getFullName(),
            application.getInternship().getTitle(),
            status
        );
        return saved;
    }
}
