package com.klef.fsad.sdp.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "course_enrollment")
public class CourseEnrollment 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	private Courses course;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime enrolledAt;
	
	@Column(nullable = false)
	private double progress = 0.0;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Courses getCourse() {
		return course;
	}

	public void setCourse(Courses course) {
		this.course = course;
	}

	public LocalDateTime getEnrolledAt() {
		return enrolledAt;
	}

	public void setEnrolledAt(LocalDateTime enrolledAt) {
		this.enrolledAt = enrolledAt;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

	@Override
	public String toString() {
		return "CourseEnrollment [id=" + id + ", student=" + student.getId() + ", course=" + course.getId()
				+ ", enrolledAt=" + enrolledAt + ", progress=" + progress + "]";
	}
}
