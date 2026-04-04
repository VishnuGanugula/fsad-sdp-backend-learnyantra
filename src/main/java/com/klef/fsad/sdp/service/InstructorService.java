package com.klef.fsad.sdp.service;

import java.util.List;

import com.klef.fsad.sdp.dto.InstructorDTO;
import com.klef.fsad.sdp.entity.Courses;
import com.klef.fsad.sdp.entity.Student;

public interface InstructorService 
{
	public InstructorDTO verifyInstructorLogin(String email,String pwd);
    
	public String addCourse(Courses course);
	public List<Courses> viewCourseDetailsByInstructor(int instructorid); // instructor id
	public String deleteCourse(int courseid);
	
	public List<Student> viewStudentsRegisteredInCourse(int courseid);
	
	public List<Courses> viewPublishedCourses();
	public List<Courses> searchCourses(String keyword);
	public String updateCourseStatus(int id, boolean status);
	public List<InstructorDTO> viewAllInstructors();
}