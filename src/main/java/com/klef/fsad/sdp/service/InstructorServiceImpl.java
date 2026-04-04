package com.klef.fsad.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.dto.InstructorDTO;
import com.klef.fsad.sdp.entity.Courses;
import com.klef.fsad.sdp.entity.Instructor;
import com.klef.fsad.sdp.entity.Student;
import com.klef.fsad.sdp.repository.InstructorRepository;
import com.klef.fsad.sdp.repository.CourseRepository;

@Service
public class InstructorServiceImpl implements InstructorService
{
	@Autowired
	private InstructorRepository instructorRepository;
	
	@Autowired
	private CourseRepository courseRepository;

	@Override
	public InstructorDTO verifyInstructorLogin(String email, String pwd) 
	{
		Instructor ins = instructorRepository.findByEmailAndPassword(email, pwd);
		
		if(ins != null)
		{
			InstructorDTO dto = new InstructorDTO();
			dto.setId(ins.getId());
			dto.setUsername(ins.getUsername());
			dto.setEmail(ins.getEmail());
			dto.setFirstName(ins.getFirstName());
			dto.setLastName(ins.getLastName());
			dto.setGender(ins.getGender());
			dto.setLocation(ins.getLocation());
			
			return dto;
		}
		return null;
	}

	@Override
	public String addCourse(Courses course) {
		courseRepository.save(course);
		return "Course Added Successfully";
	}

	@Override
	public List<Courses> viewCourseDetailsByInstructor(int instructorid) {
		return courseRepository.findCoursesByInstructor(instructorid);
	}

	@Override
	public String deleteCourse(int courseid) {
		courseRepository.deleteById(courseid);
		return "Course Deleted Successfully";
	}

	@Override
	public List<Student> viewStudentsRegisteredInCourse(int courseid) {
		Courses course = courseRepository.findById(courseid).orElse(null);
		if(course != null)
		{
			return course.getStudents();
		}
		return null;
	}

	@Override
	public List<Courses> viewPublishedCourses() {
		return courseRepository.findPublishedCourses();
	}

	@Override
	public List<Courses> searchCourses(String keyword) {
		return courseRepository.searchCourses(keyword);
	}

	@Override
	public String updateCourseStatus(int id, boolean status) {
		int updated = courseRepository.updateCourseStatus(id, status);
		if(updated > 0)
		{
			return "Course Status Updated Successfully";
		}
		return "Course Not Found";
	}

	@Override
	public List<InstructorDTO> viewAllInstructors() 
	{
		List<Instructor> instructors = instructorRepository.findAll();
		
		List<InstructorDTO> dtoList = new java.util.ArrayList<>();
		
		for(Instructor ins : instructors)
		{
			InstructorDTO dto = new InstructorDTO();
			dto.setId(ins.getId());
			dto.setUsername(ins.getUsername());
			dto.setEmail(ins.getEmail());
			dto.setFirstName(ins.getFirstName());
			dto.setLastName(ins.getLastName());
			dto.setGender(ins.getGender());
			dto.setLocation(ins.getLocation());
			
			dtoList.add(dto);
		}
		
		return dtoList;
	}
}