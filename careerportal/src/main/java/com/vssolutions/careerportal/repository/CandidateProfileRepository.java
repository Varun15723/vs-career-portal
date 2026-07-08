package com.vssolutions.careerportal.repository;

import com.vssolutions.careerportal.model.CandidateProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for CandidateProfile CRUD and custom queries.
 *
 * Folder: careerportal/src/main/java/com/vscareerportal/careerportal/repository/CandidateProfileRepository.java
 */
@Repository
public interface CandidateProfileRepository extends JpaRepository<CandidateProfile, Long> {

    Optional<CandidateProfile> findByUserId(Long userId);

    Optional<CandidateProfile> findByEmail(String email);

    boolean existsByUserId(Long userId);

    boolean existsByEmail(String email);

    List<CandidateProfile> findByIsAvailableTrue();

    List<CandidateProfile> findByCityIgnoreCase(String city);

    @Query("SELECT c FROM CandidateProfile c WHERE LOWER(c.skills) LIKE LOWER(CONCAT('%', :skill, '%'))")
    List<CandidateProfile> findBySkill(@Param("skill") String skill);

    @Query("SELECT c FROM CandidateProfile c WHERE c.experienceYears >= :minYears AND c.experienceYears <= :maxYears")
    List<CandidateProfile> findByExperienceRange(@Param("minYears") int minYears, @Param("maxYears") int maxYears);

    @Query("SELECT c FROM CandidateProfile c WHERE LOWER(c.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<CandidateProfile> searchByName(@Param("name") String name);
}

