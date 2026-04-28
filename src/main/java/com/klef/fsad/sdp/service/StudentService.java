package com.klef.fsad.sdp.service;

import java.util.List;

import com.klef.fsad.sdp.entity.CourseEnrollment;
import com.klef.fsad.sdp.entity.Courses;
import com.klef.fsad.sdp.entity.Student;
import com.klef.fsad.sdp.entity.Module;
import com.klef.fsad.sdp.entity.Assignment;
import com.klef.fsad.sdp.entity.Submission;

public interface StudentService 
{	
	public String studentRegistration(Student student);
	public Student verfiyStudentLogin(String email, String pwd);
	public Student getStudentById(int id);
	public String updateStudentProfile(Student student);
	
	public String enrollInCourse(int studentId, long courseId);
	public String unenrollFromCourse(int studentId, long courseId);

	public List<CourseEnrollment> getStudentEnrollments(int studentId);
	public List<Courses> getStudentEnrolledCourses(int studentId);
	public Student displayStudentById(int id);

	// New logic
	public List<Module> getModulesByCourse(long courseId);
	public List<Assignment> getAssignmentsByCourse(long courseId);
	public String submitAssignment(Submission submission);
	public Submission getSubmissionStatus(long assignmentId, int studentId);
}
