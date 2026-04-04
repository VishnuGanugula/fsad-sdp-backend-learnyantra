package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klef.fsad.sdp.entity.CourseEnrollment;
import com.klef.fsad.sdp.entity.Courses;
import com.klef.fsad.sdp.service.StudentService;

@RestController
@RequestMapping("enrollmentapi")
@CrossOrigin("*")
public class CourseEnrollmentController
{
	@Autowired
	private StudentService studentService;

	@GetMapping("/")
	public String enrollmentHome()
	{
		return "Course Enrollment Controller Demo";
	}

	@PostMapping("/enroll/{studentid}/{courseid}")
	public ResponseEntity<String> enrollInCourse(@PathVariable int studentid, @PathVariable long courseid)
	{
		try
		{
			String output = studentService.enrollInCourse(studentid, courseid);
			if(output.contains("Successfully"))
			{
				return ResponseEntity.status(201).body(output);
			}
			return ResponseEntity.status(400).body(output);
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}

	@DeleteMapping("/unenroll/{studentid}/{courseid}")
	public ResponseEntity<String> unenrollFromCourse(@PathVariable int studentid, @PathVariable long courseid)
	{
		try
		{
			String output = studentService.unenrollFromCourse(studentid, courseid);
			if(output.contains("Successfully"))
			{
				return ResponseEntity.status(200).body(output);
			}
			return ResponseEntity.status(400).body(output);
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}

	@GetMapping("/enrollments/{studentid}")
	public ResponseEntity<?> getStudentEnrollments(@PathVariable int studentid)
	{
		try
		{
			List<CourseEnrollment> enrollments = studentService.getStudentEnrollments(studentid);
			if(enrollments == null || enrollments.isEmpty())
			{
				return ResponseEntity.status(204).body("No Enrollments Found");
			}
			return ResponseEntity.status(200).body(enrollments);
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}

	@GetMapping("/enrolled-courses/{studentid}")
	public ResponseEntity<?> getStudentEnrolledCourses(@PathVariable int studentid)
	{
		try
		{
			List<Courses> courses = studentService.getStudentEnrolledCourses(studentid);
			if(courses == null || courses.isEmpty())
			{
				return ResponseEntity.status(204).body("No Courses Enrolled");
			}
			return ResponseEntity.status(200).body(courses);
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}
}
