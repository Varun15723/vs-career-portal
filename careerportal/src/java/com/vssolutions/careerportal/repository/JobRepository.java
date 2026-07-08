package com.vssolutions.careerportal.repository;

import com.vssolutions.careerportal.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByIsActiveTrue();
    List<Job> findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCase(String title, String company);
    List<Job> findByPostedById(Long recruiterId);

    // NEW — Filter jobs by location, job type, experience, department.
    // Any parameter can be null - null means "don't filter on this field"
    @Query("SELECT j FROM Job j WHERE j.isActive = true " +
           "AND (:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))) " +
           "AND (:jobType IS NULL OR j.jobType = :jobType) " +
           "AND (:experience IS NULL OR j.experience = :experience) " +
           "AND (:department IS NULL OR LOWER(j.department) LIKE LOWER(CONCAT('%', :department, '%')))")
    List<Job> filterJobs(@Param("location") String location,
                          @Param("jobType") String jobType,
                          @Param("experience") String experience,
                          @Param("department") String department);

    // --- Dashboard Analytics ---

    long countByIsActiveTrue();

    // Number of jobs grouped by department, e.g. [["Engineering", 5], ["Sales", 2]]
    @Query("SELECT COALESCE(j.department, 'Unspecified'), COUNT(j) FROM Job j GROUP BY j.department")
    List<Object[]> countByDepartment();

    // Number of jobs grouped by location
    @Query("SELECT j.location, COUNT(j) FROM Job j GROUP BY j.location")
    List<Object[]> countByLocation();
}
