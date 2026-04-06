package com.klef.fsad.sdp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.Courses;
import com.klef.fsad.sdp.entity.Instructor;
import com.klef.fsad.sdp.entity.Student;
import com.klef.fsad.sdp.entity.CourseEnrollment;
import com.klef.fsad.sdp.repository.CourseRepository;
import com.klef.fsad.sdp.repository.InstructorRepository;
import com.klef.fsad.sdp.repository.CourseEnrollmentRepository;

@Service
public class InstructorServiceImpl implements InstructorService
{
	@Autowired
	private InstructorRepository instructorRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private CourseEnrollmentRepository courseEnrollmentRepository;

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
		// Get enrollments for this course from CourseEnrollment table
		List<CourseEnrollment> enrollments = courseEnrollmentRepository.findByCourseId(courseid);
		
		// Extract StudentDTO from enrollments
		if(enrollments != null && !enrollments.isEmpty()) {
			return enrollments.stream()
					.map(enrollment -> enrollment.getStudent())
					.collect(Collectors.toList());
		}
		return null;
	}
}