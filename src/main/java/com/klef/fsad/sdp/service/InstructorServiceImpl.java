package com.klef.fsad.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.Courses;
import com.klef.fsad.sdp.entity.Instructor;
import com.klef.fsad.sdp.entity.Student;
import com.klef.fsad.sdp.repository.CourseRepository;
import com.klef.fsad.sdp.repository.InstructorRepository;

@Service
public class InstructorServiceImpl implements InstructorService
{
	@Autowired
	private InstructorRepository instructorRepository;
	
	@Autowired
	private CourseRepository courseRepository;

	@Override
	public Instructor verifyInstructorLogin(String email, String pwd) 
	{
		return instructorRepository.findByEmailAndPassword(email, pwd);
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
}