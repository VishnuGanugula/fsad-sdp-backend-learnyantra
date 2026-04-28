package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.entity.CourseEnrollment;
import com.klef.fsad.sdp.service.CourseEnrollmentService;

@RestController
@RequestMapping("enrollmentapi")
@CrossOrigin("*")
public class CourseEnrollmentController
{
	@Autowired
	private CourseEnrollmentService courseEnrollmentService;

	@GetMapping("/")
	public String enrollmentHome()
	{
		return "Course Enrollment Controller Demo";
	}

	@PostMapping("/enroll/{studentid}/{courseid}")
	public ResponseEntity<String> enrollStudent(@PathVariable int studentid, @PathVariable long courseid)
	{
		try
		{
			String output = courseEnrollmentService.enrollStudent(studentid, courseid);
			return ResponseEntity.status(200).body(output);
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}

	@DeleteMapping("/unenroll/{studentid}/{courseid}")
	public ResponseEntity<String> unenrollStudent(@PathVariable int studentid, @PathVariable long courseid)
	{
		try
		{
			String output = courseEnrollmentService.unenrollStudent(studentid, courseid);
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

	@GetMapping("/studentcourses/{studentid}")
	public ResponseEntity<?> getStudentCourses(@PathVariable int studentid)
	{
		try
		{
			List<CourseEnrollment> list = courseEnrollmentService.getStudentCourses(studentid);
			return ResponseEntity.ok().body(list);
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Error Fetching Data");
		}
	}

	@GetMapping("/coursestudents/{courseid}")
	public ResponseEntity<?> getCourseStudents(@PathVariable long courseid)
	{
		try
		{
			List<CourseEnrollment> list = courseEnrollmentService.getCourseStudents(courseid);
			return ResponseEntity.ok().body(list);
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Error Fetching Data");
		}
	}

	@PutMapping("/updateprogress/{studentid}/{courseid}/{progress}")
	public ResponseEntity<String> updateProgress(@PathVariable int studentid, @PathVariable long courseid, @PathVariable int progress)
	{
		try
		{
			String output = courseEnrollmentService.updateProgress(studentid, courseid, progress);
			return ResponseEntity.ok().body(output);
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Error Updating Progress");
		}
	}

	@GetMapping("/check/{studentid}/{courseid}")
	public ResponseEntity<?> isStudentEnrolled(@PathVariable int studentid, @PathVariable long courseid)
	{
		try
		{
			boolean status = courseEnrollmentService.isStudentEnrolled(studentid, courseid);
			return ResponseEntity.ok().body(status);
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Error Checking Enrollment");
		}
	}
}