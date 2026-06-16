package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.model.Candidate;
import com.vssolutions.careerportal.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    // Register Candidate
    public Candidate registerCandidate(Candidate candidate) {
        if (candidateRepository.existsByEmail(candidate.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        candidate.setPassword(passwordEncoder.encode(candidate.getPassword()));
        Candidate saved = candidateRepository.save(candidate);
        //emailService.sendWelcomeEmail(saved.getEmail(), saved.getFullName());
        return saved;
    }

    // Find By Email
    public Optional<Candidate> findByEmail(String email) {
        return candidateRepository.findByEmail(email);
    }

    // Find By ID
    public Optional<Candidate> findById(Long id) {
        return candidateRepository.findById(id);
    }

    // Update Profile
    public Candidate updateProfile(Long id, Candidate updatedData) {
        Candidate candidate = candidateRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Candidate not found"));

        if (updatedData.getFullName() != null)  candidate.setFullName(updatedData.getFullName());
        if (updatedData.getPhone() != null)     candidate.setPhone(updatedData.getPhone());
        if (updatedData.getAddress() != null)   candidate.setAddress(updatedData.getAddress());
        if (updatedData.getSkills() != null)    candidate.setSkills(updatedData.getSkills());

        return candidateRepository.save(candidate);
    }

    // Update Resume URL
    public Candidate updateResumeUrl(Long id, String resumeUrl) {
        Candidate candidate = candidateRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Candidate not found"));
        candidate.setResumeUrl(resumeUrl);
        return candidateRepository.save(candidate);
    }

    // Get All Candidates
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    // Delete Candidate
    public void deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
    }
}