package com.klef.fsad.sdp.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

@Entity
@Table(name = "submission_table")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String submissionUrl; // Link to uploaded file

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime submittedAt;

    private Double grade; // Null until instructor grades it

    @Column(columnDefinition = "TEXT")
    private String feedback;

    // Prevent infinite JSON recursion: include only id, title, maxPoints, dueDate from Assignment
    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    @JsonIgnoreProperties({"course", "instructions", "questionFileUrl", "createdAt"})
    private Assignment assignment;

    // Prevent infinite JSON recursion: include only basic student fields
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnoreProperties({"password", "enrollments", "submissions"})
    private Student student;

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getSubmissionUrl() { return submissionUrl; }
    public void setSubmissionUrl(String submissionUrl) { this.submissionUrl = submissionUrl; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }

    public Double getGrade() { return grade; }
    public void setGrade(Double grade) { this.grade = grade; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }

    public Assignment getAssignment() { return assignment; }
    public void setAssignment(Assignment assignment) { this.assignment = assignment; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
}
