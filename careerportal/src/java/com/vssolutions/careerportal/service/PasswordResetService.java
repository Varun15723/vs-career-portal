package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.model.Candidate;
import com.vssolutions.careerportal.model.PasswordResetToken;
import com.vssolutions.careerportal.model.Recruiter;
import com.vssolutions.careerportal.repository.CandidateRepository;
import com.vssolutions.careerportal.repository.PasswordResetTokenRepository;
import com.vssolutions.careerportal.repository.RecruiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Works for both candidates and recruiters - looks up whichever table has the email
    public String createResetToken(String email) {
        String userType;

        if (candidateRepository.findByEmail(email).isPresent()) {
            userType = "candidate";
        } else if (recruiterRepository.findByEmail(email).isPresent()) {
            userType = "recruiter";
        } else {
            throw new RuntimeException("No account found with this email");
        }

        tokenRepository.deleteByEmail(email);

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setEmail(email);
        resetToken.setUserType(userType);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        resetToken.setUsed(false);
        tokenRepository.save(resetToken);

        String resetLink = "http://localhost:3000/reset-password?token=" + token; // update to your real frontend URL
        emailService.sendPasswordResetEmail(email, resetLink);

        return "Password reset link sent to your email";
    }

    public String resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
            .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        if (resetToken.isUsed()) {
            throw new RuntimeException("This reset link has already been used");
        }
        if (resetToken.isExpired()) {
            throw new RuntimeException("This reset link has expired");
        }

        if ("candidate".equals(resetToken.getUserType())) {
            Candidate candidate = candidateRepository.findByEmail(resetToken.getEmail())
                .orElseThrow(() -> new RuntimeException("Account not found"));
            candidate.setPassword(passwordEncoder.encode(newPassword));
            candidateRepository.save(candidate);
        } else {
            Recruiter recruiter = recruiterRepository.findByEmail(resetToken.getEmail())
                .orElseThrow(() -> new RuntimeException("Account not found"));
            recruiter.setPassword(passwordEncoder.encode(newPassword));
            recruiterRepository.save(recruiter);
        }

        resetToken.setUsed(true);
        tokenRepository.save(resetToken);

        return "Password reset successful. You can now log in with your new password.";
    }
}
