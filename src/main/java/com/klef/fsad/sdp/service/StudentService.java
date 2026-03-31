package com.klef.fsad.sdp.service;

import java.util.List;

import com.klef.fsad.sdp.entity.CourseEnrollment;
import com.klef.fsad.sdp.entity.Courses;
import com.klef.fsad.sdp.entity.Student;

public  interface StudentService 
{	
	public String studentRegistration(Student student);
	public Student verfiyStudentLogin(String email, String pwd);
	public String updateStudentProfile(Student student);
	
	public String enrollInCourse(int studentId, long courseId);
	public String unenrollFromCourse(int studentId, long courseId);
	public List<CourseEnrollment> getStudentEnrollments(int studentId);
	public List<Courses> getStudentEnrolledCourses(int studentId);
	
}
