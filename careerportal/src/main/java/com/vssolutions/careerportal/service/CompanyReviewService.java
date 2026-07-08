package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.model.Candidate;
import com.vssolutions.careerportal.model.CompanyReview;
import com.vssolutions.careerportal.repository.CompanyReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CompanyReviewService {

    @Autowired
    private CompanyReviewRepository companyReviewRepository;

    public CompanyReview addReview(Candidate candidate, String companyName, Integer rating, String reviewText) {
        if (rating == null || rating < 1 || rating > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }
        if (companyReviewRepository.existsByCandidateIdAndCompanyNameIgnoreCase(candidate.getId(), companyName)) {
            throw new RuntimeException("You have already reviewed this company");
        }

        CompanyReview review = new CompanyReview();
        review.setCandidate(candidate);
        review.setCompanyName(companyName);
        review.setRating(rating);
        review.setReviewText(reviewText);
        return companyReviewRepository.save(review);
    }

    public List<CompanyReview> getReviews(String companyName) {
        return companyReviewRepository.findByCompanyNameIgnoreCaseOrderByCreatedAtDesc(companyName);
    }

    public Map<String, Object> getSummary(String companyName) {
        Double avg = companyReviewRepository.getAverageRating(companyName);
        long count = companyReviewRepository.countByCompanyNameIgnoreCase(companyName);
        return Map.of(
            "companyName", companyName,
            "averageRating", avg == null ? 0.0 : Math.round(avg * 10.0) / 10.0,
            "totalReviews", count
        );
    }
}
