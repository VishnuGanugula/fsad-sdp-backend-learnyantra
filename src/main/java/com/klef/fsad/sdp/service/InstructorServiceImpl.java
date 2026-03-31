package com.klef.fsad.sdp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.Courses;
import com.klef.fsad.sdp.entity.CourseEnrollment;
import com.klef.fsad.sdp.entity.Instructor;
import com.klef.fsad.sdp.entity.Student;
import com.klef.fsad.sdp.repository.InstructorRepository;
import com.klef.fsad.sdp.repository.CourseEnrollmentRepository;
import com.klef.fsad.sdp.repository.CourseRepository;

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
		// Set course to unpublished (draft) by default - admin must approve
		course.setPublished(false);
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
	public List<Student> viewStudentsRegisteredInCourse(long courseid) {
		List<CourseEnrollment> enrollments = courseEnrollmentRepository.findByCourseId(courseid);
		return enrollments.stream()
				.map(CourseEnrollment::getStudent)
				.collect(Collectors.toList());
	}

	@Override
	public List<CourseEnrollment> getEnrolledStudentsForCourse(long courseId) {
		return courseEnrollmentRepository.findByCourseId(courseId);
	}

	@Override
	public List<Courses> viewAllCourses() {
		return courseRepository.findAll();
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
}