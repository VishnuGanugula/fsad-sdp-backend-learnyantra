package com.klef.fsad.sdp.service;

import java.util.List;

import com.klef.fsad.sdp.entity.Courses;
import com.klef.fsad.sdp.entity.Instructor;
import com.klef.fsad.sdp.entity.Student;
import com.klef.fsad.sdp.entity.Module;
import com.klef.fsad.sdp.entity.Assignment;
import com.klef.fsad.sdp.entity.Submission;

public interface InstructorService 
{
	public Instructor verifyInstructorLogin(String email,String pwd);
    
	public String addCourse(Courses course);
	public List<Courses> viewCourseDetailsByInstructor(int instructorid);
	public String deleteCourse(int courseid);
	
	public List<Student> viewStudentsRegisteredInCourse(int courseid);

	// Module management
	public String addModule(Module module);
	public List<Module> getModulesByCourse(long courseId);
	
	// Assignment management
	public String addAssignment(Assignment assignment);
	public List<Assignment> getAssignmentsByCourse(long courseId);
	
	// Submission & Grading
	public List<Submission> getSubmissionsByAssignment(long assignmentId);
	public String gradeSubmission(long submissionId, double grade, String feedback);
}