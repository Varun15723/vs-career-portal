package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.model.Recruiter;
import com.vssolutions.careerportal.repository.RecruiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RecruiterService {

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register Recruiter
    public Recruiter registerRecruiter(Recruiter recruiter) {
        if (recruiterRepository.existsByEmail(recruiter.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        recruiter.setPassword(passwordEncoder.encode(recruiter.getPassword()));
        recruiter.setRole("recruiter");
        return recruiterRepository.save(recruiter);
    }

    // Find By Email
    public Optional<Recruiter> findByEmail(String email) {
        return recruiterRepository.findByEmail(email);
    }

    // Find By ID
    public Optional<Recruiter> findById(Long id) {
        return recruiterRepository.findById(id);
    }

    // Update Profile
    public Recruiter updateProfile(Long id, Recruiter updatedData) {
        Recruiter recruiter = recruiterRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Recruiter not found"));

        if (updatedData.getName() != null)    recruiter.setName(updatedData.getName());
        if (updatedData.getCompany() != null) recruiter.setCompany(updatedData.getCompany());

        return recruiterRepository.save(recruiter);
    }

    // Get All Recruiters
    public List<Recruiter> getAllRecruiters() {
        return recruiterRepository.findAll();
    }

    // Delete Recruiter
    public void deleteRecruiter(Long id) {
        recruiterRepository.deleteById(id);
    }
}
