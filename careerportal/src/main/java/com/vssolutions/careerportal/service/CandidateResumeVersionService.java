package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.model.CandidateResumeVersion;
import com.vssolutions.careerportal.repository.CandidateResumeVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class CandidateResumeVersionService {

    @Autowired
    private CandidateResumeVersionRepository resumeVersionRepository;

    // Matches the upload directory already configured for the main resume upload feature
    @Value("${file.upload-dir:uploads/resumes}")
    private String uploadDir;

    @Transactional
    public CandidateResumeVersion uploadVersion(Long candidateId, String versionLabel,
                                                 Boolean isPrimary, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Resume file is required");
        }
        if (versionLabel == null || versionLabel.isBlank()) {
            throw new RuntimeException("Version label is required (e.g. 'Java Developer Resume v2')");
        }

        String originalName = file.getOriginalFilename();
        String storedName = UUID.randomUUID() + "_" + (originalName != null ? originalName : "resume.pdf");

        try {
            Path dirPath = Paths.get(uploadDir);
            Files.createDirectories(dirPath);
            Path target = dirPath.resolve(storedName);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save resume file: " + e.getMessage());
        }

        if (Boolean.TRUE.equals(isPrimary)) {
            clearExistingPrimary(candidateId);
        }

        CandidateResumeVersion version = new CandidateResumeVersion();
        version.setCandidateId(candidateId);
        version.setVersionLabel(versionLabel);
        version.setFileUrl("/" + uploadDir + "/" + storedName);
        version.setFileName(originalName);
        version.setFileSizeKb((int) (file.getSize() / 1024));
        version.setIsPrimary(Boolean.TRUE.equals(isPrimary));

        return resumeVersionRepository.save(version);
    }

    public List<CandidateResumeVersion> getVersions(Long candidateId) {
        return resumeVersionRepository.findByCandidateId(candidateId);
    }

    @Transactional
    public CandidateResumeVersion setPrimaryVersion(Long id, Long candidateId) {
        CandidateResumeVersion target = resumeVersionRepository.findByIdAndCandidateId(id, candidateId)
            .orElseThrow(() -> new RuntimeException("Resume version not found or doesn't belong to you"));
        clearExistingPrimary(candidateId);
        target.setIsPrimary(true);
        return resumeVersionRepository.save(target);
    }

    private void clearExistingPrimary(Long candidateId) {
        List<CandidateResumeVersion> existing = resumeVersionRepository.findByCandidateId(candidateId);
        existing.forEach(r -> r.setIsPrimary(false));
        resumeVersionRepository.saveAll(existing);
    }

    public void deleteVersion(Long id, Long candidateId) {
        CandidateResumeVersion existing = resumeVersionRepository.findByIdAndCandidateId(id, candidateId)
            .orElseThrow(() -> new RuntimeException("Resume version not found or doesn't belong to you"));
        resumeVersionRepository.delete(existing);
    }
}
