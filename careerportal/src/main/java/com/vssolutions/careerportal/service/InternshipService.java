package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.model.Internship;
import com.vssolutions.careerportal.repository.InternshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InternshipService {

    @Autowired
    private InternshipRepository internshipRepository;

    public List<Internship> getAllInternships() {
        return internshipRepository.findByIsActiveTrue();
    }

    public Optional<Internship> getById(Long id) {
        return internshipRepository.findById(id);
    }

    public List<Internship> search(String query) {
        return internshipRepository.findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCase(query, query);
    }

    public Internship createInternship(Internship internship) {
        return internshipRepository.save(internship);
    }

    public Internship updateInternship(Long id, Internship updated) {
        Internship internship = internshipRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Internship not found"));

        if (updated.getTitle() != null) internship.setTitle(updated.getTitle());
        if (updated.getCompany() != null) internship.setCompany(updated.getCompany());
        if (updated.getDepartment() != null) internship.setDepartment(updated.getDepartment());
        if (updated.getLocation() != null) internship.setLocation(updated.getLocation());
        if (updated.getDurationMonths() != null) internship.setDurationMonths(updated.getDurationMonths());
        if (updated.getIsPaid() != null) internship.setIsPaid(updated.getIsPaid());
        if (updated.getStipend() != null) internship.setStipend(updated.getStipend());
        if (updated.getDescription() != null) internship.setDescription(updated.getDescription());
        if (updated.getRequirements() != null) internship.setRequirements(updated.getRequirements());
        if (updated.getStartDate() != null) internship.setStartDate(updated.getStartDate());

        return internshipRepository.save(internship);
    }

    public void deleteInternship(Long id) {
        internshipRepository.deleteById(id);
    }

    public List<Internship> getByRecruiter(Long recruiterId) {
        return internshipRepository.findByPostedById(recruiterId);
    }
}
