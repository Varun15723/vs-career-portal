package com.vssolutions.careerportal.repository;

import com.vssolutions.careerportal.model.CompanyReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CompanyReviewRepository extends JpaRepository<CompanyReview, Long> {
    List<CompanyReview> findByCompanyNameIgnoreCaseOrderByCreatedAtDesc(String companyName);

    @Query("SELECT AVG(c.rating) FROM CompanyReview c WHERE LOWER(c.companyName) = LOWER(:companyName)")
    Double getAverageRating(@Param("companyName") String companyName);

    long countByCompanyNameIgnoreCase(String companyName);

    boolean existsByCandidateIdAndCompanyNameIgnoreCase(Long candidateId, String companyName);
}
