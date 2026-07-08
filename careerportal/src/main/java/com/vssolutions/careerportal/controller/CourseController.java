package com.vssolutions.careerportal.controller;

import com.vssolutions.careerportal.model.Candidate;
import com.vssolutions.careerportal.model.Course;
import com.vssolutions.careerportal.service.CandidateService;
import com.vssolutions.careerportal.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CandidateService candidateService;

    // Public - browsing the catalog needs no login
    @GetMapping
    public ResponseEntity<?> getAllCourses(@RequestParam(required = false) String category) {
        if (category != null) {
            return ResponseEntity.ok(courseService.getByCategory(category));
        }
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourse(@PathVariable Long id) {
        return courseService.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Admin only - matches "/api/admin/**".hasRole("ADMIN"), mount under /api/admin/courses instead
    // if you want that enforced automatically (see PATCH_NOTES.md). Left as /api/courses here
    // so recruiters can also curate content - adjust based on who should own this.
    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.createCourse(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        try {
            return ResponseEntity.ok(courseService.updateCourse(id, course));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok(Map.of("message", "Course deactivated"));
    }

    // Candidate only
    @PostMapping("/{id}/enroll")
    public ResponseEntity<?> enroll(@PathVariable Long id, Authentication auth) {
        try {
            Candidate candidate = candidateService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
            return ResponseEntity.ok(courseService.enroll(candidate, id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> markComplete(@PathVariable Long id, Authentication auth) {
        try {
            Candidate candidate = candidateService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
            return ResponseEntity.ok(courseService.markComplete(candidate.getId(), id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/my/enrollments")
    public ResponseEntity<?> myEnrollments(Authentication auth) {
        try {
            Candidate candidate = candidateService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
            return ResponseEntity.ok(courseService.getMyEnrollments(candidate.getId()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
