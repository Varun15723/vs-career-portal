package com.vssolutions.careerportal.repository;

import com.vssolutions.careerportal.model.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OtpTokenRepository extends JpaRepository<OtpToken, Long> {
    Optional<OtpToken> findTopByEmailAndPurposeOrderByIdDesc(String email, String purpose);
    void deleteByEmailAndPurpose(String email, String purpose);
}
