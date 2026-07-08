package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.model.Candidate;
import com.vssolutions.careerportal.model.Course;
import com.vssolutions.careerportal.model.CourseEnrollment;
import com.vssolutions.careerportal.repository.CourseEnrollmentRepository;
import com.vssolutions.careerportal.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// DECISION: internal course catalog (title/description/skills), with an optional
// externalUrl field for courses that just link out to Coursera/Udemy/etc.
// If you want full external-provider sync later, this model already has room for it.
@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseEnrollmentRepository enrollmentRepository;

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findByIsActiveTrue();
    }

    public List<Course> getByCategory(String category) {
        return courseRepository.findByCategoryIgnoreCaseAndIsActiveTrue(category);
    }

    public Optional<Course> getById(Long id) {
        return courseRepository.findById(id);
    }

    public Course updateCourse(Long id, Course updated) {
        Course course = courseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Course not found"));
        if (updated.getTitle() != null) course.setTitle(updated.getTitle());
        if (updated.getDescription() != null) course.setDescription(updated.getDescription());
        if (updated.getCategory() != null) course.setCategory(updated.getCategory());
        if (updated.getLevel() != null) course.setLevel(updated.getLevel());
        if (updated.getDurationHours() != null) course.setDurationHours(updated.getDurationHours());
        if (updated.getExternalUrl() != null) course.setExternalUrl(updated.getExternalUrl());
        if (updated.getSkillsCovered() != null) course.setSkillsCovered(updated.getSkillsCovered());
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Course not found"));
        course.setIsActive(false); // soft delete - keeps enrollment history intact
        courseRepository.save(course);
    }

    public CourseEnrollment enroll(Candidate candidate, Long courseId) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));

        if (enrollmentRepository.findByCandidateIdAndCourseId(candidate.getId(), courseId).isPresent()) {
            throw new RuntimeException("Already enrolled in this course");
        }

        CourseEnrollment enrollment = new CourseEnrollment();
        enrollment.setCandidate(candidate);
        enrollment.setCourse(course);
        return enrollmentRepository.save(enrollment);
    }

    public CourseEnrollment markComplete(Long candidateId, Long courseId) {
        CourseEnrollment enrollment = enrollmentRepository.findByCandidateIdAndCourseId(candidateId, courseId)
            .orElseThrow(() -> new RuntimeException("Not enrolled in this course"));
        enrollment.setCompleted(true);
        enrollment.setCompletedAt(LocalDateTime.now());
        return enrollmentRepository.save(enrollment);
    }

    public List<CourseEnrollment> getMyEnrollments(Long candidateId) {
        return enrollmentRepository.findByCandidateId(candidateId);
    }
}
