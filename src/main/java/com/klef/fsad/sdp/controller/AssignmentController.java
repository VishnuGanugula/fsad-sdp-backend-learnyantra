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

    @PostMapping("/submit")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<String> submitAssignment(
            @RequestParam("assignmentId") long assignmentId,
            @RequestParam("studentId") int studentId,
            @RequestParam("file") MultipartFile file) {
        try {
            String fileName = fileStorageService.storeFile(file);
            
            Submission submission = new Submission();
            submission.setSubmissionUrl("/uploads/" + fileName);
            
            Assignment assignment = new Assignment();
            assignment.setId(assignmentId);
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

    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'INSTRUCTOR')")
    public ResponseEntity<List<Assignment>> getAssignmentsByCourse(@PathVariable long courseId) {
        List<Assignment> assignments = studentService.getAssignmentsByCourse(courseId);
        return ResponseEntity.ok(assignments);
    }

    @PostMapping("/grade")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public ResponseEntity<String> gradeSubmission(
            @RequestParam("submissionId") long submissionId,
            @RequestParam("grade") double grade,
            @RequestParam("feedback") String feedback) {
        String result = instructorService.gradeSubmission(submissionId, grade, feedback);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/status")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<Submission> getSubmissionStatus(
            @RequestParam("assignmentId") long assignmentId,
            @RequestParam("studentId") int studentId) {
        Submission submission = studentService.getSubmissionStatus(assignmentId, studentId);
        return ResponseEntity.ok(submission);
    }
}
