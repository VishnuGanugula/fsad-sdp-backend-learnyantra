package com.klef.fsad.sdp.controller;

import com.klef.fsad.sdp.entity.Assignment;
import com.klef.fsad.sdp.entity.Submission;
import com.klef.fsad.sdp.entity.Courses;
import com.klef.fsad.sdp.entity.Student;
import com.klef.fsad.sdp.service.InstructorService;
import com.klef.fsad.sdp.service.StudentService;
import com.klef.fsad.sdp.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private FileStorageService fileStorageService;

    // ─── Instructor: Create Assignment ────────────────────────────────────────
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public ResponseEntity<String> addAssignment(
            @RequestParam("title") String title,
            @RequestParam("instructions") String instructions,
            @RequestParam("dueDate") String dueDate,
            @RequestParam("maxPoints") int maxPoints,
            @RequestParam("courseId") long courseId,
            @RequestParam(value = "questionFile", required = false) MultipartFile questionFile) {
        try {
            Assignment assignment = new Assignment();
            assignment.setTitle(title);
            assignment.setInstructions(instructions);
            assignment.setDueDate(LocalDateTime.parse(dueDate));
            assignment.setMaxPoints(maxPoints);

            if (questionFile != null && !questionFile.isEmpty()) {
                String fileName = fileStorageService.storeFile(questionFile);
                assignment.setQuestionFileUrl("/uploads/" + fileName);
            }

            Courses course = new Courses();
            course.setId(courseId);
            assignment.setCourse(course);

            String result = instructorService.addAssignment(assignment);
            return ResponseEntity.status(201).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding assignment: " + e.getMessage());
        }
    }

    // ─── Student: Submit Assignment (with deadline & duplicate checks) ────────
    @PostMapping("/submit")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<String> submitAssignment(
            @RequestParam("assignmentId") long assignmentId,
            @RequestParam("studentId") int studentId,
            @RequestParam("file") MultipartFile file) {
        try {
            // 1. Load the full assignment to check the deadline
            Assignment assignment = studentService.getAssignmentById(assignmentId);
            if (assignment == null) {
                return ResponseEntity.status(404).body("Assignment not found.");
            }

            // 2. Deadline enforcement — reject late submissions
            if (assignment.getDueDate() != null && LocalDateTime.now().isAfter(assignment.getDueDate())) {
                return ResponseEntity.status(403)
                        .body("Submission deadline has passed. Due date was: " + assignment.getDueDate());
            }

            // 3. Prevent duplicate submissions by the same student
            Submission existing = studentService.getSubmissionStatus(assignmentId, studentId);
            if (existing != null) {
                return ResponseEntity.status(409)
                        .body("You have already submitted this assignment.");
            }

            // 4. Persist the uploaded file to disk
            if (file == null || file.isEmpty()) {
                return ResponseEntity.status(400).body("No file provided. Please attach your submission file.");
            }
            String fileName = fileStorageService.storeFile(file);

            // 5. Build and persist the submission record
            Submission submission = new Submission();
            submission.setSubmissionUrl("/uploads/" + fileName);
            submission.setAssignment(assignment);

            Student student = new Student();
            student.setId(studentId);
            submission.setStudent(student);

            String result = studentService.submitAssignment(submission);
            return ResponseEntity.status(201).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error submitting assignment: " + e.getMessage());
        }
    }

    // ─── Get Assignments by Course ────────────────────────────────────────────
    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'INSTRUCTOR')")
    public ResponseEntity<List<Assignment>> getAssignmentsByCourse(@PathVariable long courseId) {
        return ResponseEntity.ok(studentService.getAssignmentsByCourse(courseId));
    }

    // ─── Instructor: Get All Submissions for an Assignment ────────────────────
    @GetMapping("/submissions/{assignmentId}")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public ResponseEntity<List<Submission>> getSubmissionsByAssignment(@PathVariable long assignmentId) {
        return ResponseEntity.ok(instructorService.getSubmissionsByAssignment(assignmentId));
    }

    // ─── Instructor: Grade a Submission ───────────────────────────────────────
    @PostMapping("/grade")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public ResponseEntity<String> gradeSubmission(
            @RequestParam("submissionId") long submissionId,
            @RequestParam("grade") double grade,
            @RequestParam("feedback") String feedback) {
        return ResponseEntity.ok(instructorService.gradeSubmission(submissionId, grade, feedback));
    }

    // ─── Student: Check Own Submission Status ─────────────────────────────────
    @GetMapping("/status")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<Submission> getSubmissionStatus(
            @RequestParam("assignmentId") long assignmentId,
            @RequestParam("studentId") int studentId) {
        return ResponseEntity.ok(studentService.getSubmissionStatus(assignmentId, studentId));
    }
}
