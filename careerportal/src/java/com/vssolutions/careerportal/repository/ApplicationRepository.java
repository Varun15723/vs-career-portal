package com.vssolutions.careerportal.repository;

import com.vssolutions.careerportal.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByCandidateId(Long candidateId);
    List<Application> findByJobId(Long jobId);
    Optional<Application> findByCandidateIdAndJobId(Long candidateId, Long jobId);

    // --- Dashboard Analytics ---

    // Count of applications grouped by status, e.g. [["Applied", 12], ["Selected", 3]]
    @Query("SELECT a.status, COUNT(a) FROM Application a GROUP BY a.status")
    List<Object[]> countByStatus();

    // Top jobs ranked by number of applications received
    @Query("SELECT a.job.title, a.job.company, COUNT(a) as cnt FROM Application a " +
           "GROUP BY a.job.id, a.job.title, a.job.company ORDER BY cnt DESC")
    List<Object[]> countByJobOrderedDesc();

    // Applications received per recruiter (via job.postedBy)
    @Query("SELECT a.job.postedBy.name, a.job.postedBy.company, COUNT(a) FROM Application a " +
           "GROUP BY a.job.postedBy.id, a.job.postedBy.name, a.job.postedBy.company")
    List<Object[]> countByRecruiter();

    long countByStatusIgnoreCase(String status);
}