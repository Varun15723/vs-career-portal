package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.model.OtpToken;
import com.vssolutions.careerportal.repository.OtpTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

// DECISION: email OTP only (no SMS). SMS would need a paid provider (e.g. Twilio)
// which isn't set up in this project - revisit if that becomes a requirement.
@Service
public class OtpService {

    private static final int MAX_ATTEMPTS = 5;

    @Autowired
    private OtpTokenRepository otpTokenRepository;

    @Autowired
    private EmailService emailService;

    public String sendOtp(String email, String purpose) {
        otpTokenRepository.deleteByEmailAndPurpose(email, purpose);

        String code = String.format("%06d", new Random().nextInt(1_000_000));

        OtpToken otp = new OtpToken();
        otp.setEmail(email);
        otp.setCode(code);
        otp.setPurpose(purpose);
        otp.setExpiryDate(LocalDateTime.now().plusMinutes(5));
        otpTokenRepository.save(otp);

        emailService.sendOtpEmail(email, code, purpose);
        return "OTP sent to " + email;
    }

    public String verifyOtp(String email, String code, String purpose) {
        OtpToken otp = otpTokenRepository.findTopByEmailAndPurposeOrderByIdDesc(email, purpose)
            .orElseThrow(() -> new RuntimeException("No OTP requested for this email"));

        if (otp.isVerified()) {
            throw new RuntimeException("This OTP has already been used");
        }
        if (otp.isExpired()) {
            throw new RuntimeException("OTP has expired, please request a new one");
        }
        if (otp.getAttempts() >= MAX_ATTEMPTS) {
            throw new RuntimeException("Too many incorrect attempts, please request a new OTP");
        }
        if (!otp.getCode().equals(code)) {
            otp.setAttempts(otp.getAttempts() + 1);
            otpTokenRepository.save(otp);
            throw new RuntimeException("Incorrect OTP");
        }

        otp.setVerified(true);
        otpTokenRepository.save(otp);
        return "OTP verified successfully";
    }
}
