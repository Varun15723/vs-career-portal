package com.vssolutions.careerportal.repository;

import com.vssolutions.careerportal.model.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for RecruiterProfile CRUD and custom queries.
 *
 * Folder: careerportal/src/main/java/com/vscareerportal/careerportal/repository/RecruiterProfileRepository.java
 */
@Repository
public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile, Long> {

    Optional<RecruiterProfile> findByUserId(Long userId);

    Optional<RecruiterProfile> findByEmail(String email);

    boolean existsByUserId(Long userId);

    boolean existsByEmail(String email);

    List<RecruiterProfile> findByIsVerifiedTrue();

    List<RecruiterProfile> findByIndustryIgnoreCase(String industry);

    @Query("SELECT r FROM RecruiterProfile r WHERE LOWER(r.companyName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<RecruiterProfile> searchByCompanyName(@Param("name") String name);

    List<RecruiterProfile> findByCityIgnoreCase(String city);
}

