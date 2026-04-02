package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klef.fsad.sdp.entity.CourseEnrollment;
import com.klef.fsad.sdp.entity.Courses;
import com.klef.fsad.sdp.entity.Student;
import com.klef.fsad.sdp.service.StudentService;

@RestController
@RequestMapping("studentapi")
@CrossOrigin("*")
public class StudentController 
{
	
	@Autowired
	private StudentService studentservice;
	
	@GetMapping("/")
	public String studenthome() 
	{
		return "Student Controller Demo";
	}
	
	@PostMapping("/registration")
	public ResponseEntity<String> studentregistration(@RequestBody Student s) 
	{
		try
		{
			String output = studentservice.studentRegistration(s);
			return ResponseEntity.status(201).body(output);
		}
		catch (Exception e) 
		{
			return ResponseEntity.status(500).body("Internal Server Error");
		}
		
	}
	
	@PostMapping("login")
	public ResponseEntity<?> verifystudentlogin(@RequestBody Student student)
	{
		try {
			Student s = studentservice.verfiyStudentLogin(student.getEmail(), student.getPassword());
			if(s!=null)
		    {
				return ResponseEntity.status(200).body(s);
			}
			else
			{
				return ResponseEntity.status(401).body("Login Invalid");
			}
		}
		catch (Exception e) 
		{
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}
	
	  @PostMapping("/updateprofile")
	   public ResponseEntity<String> studentupdateprofile(@RequestBody Student s)
	   {
		   try
		   {
			   String output = studentservice.updateStudentProfile(s);
			   return ResponseEntity.status(201).body(output);
		   }
		   catch(Exception e)
		   {
			   return ResponseEntity.status(500).body("Internal Server Error");
		   }
	   }

	  @PostMapping("/enroll/{studentid}/{courseid}")
	  public ResponseEntity<String> enrollInCourse(@PathVariable int studentid, @PathVariable long courseid)
	  {
		  try
		  {
			  String output = studentservice.enrollInCourse(studentid, courseid);
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
			  String output = studentservice.unenrollFromCourse(studentid, courseid);
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
			  List<CourseEnrollment> enrollments = studentservice.getStudentEnrollments(studentid);
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
			  List<Courses> courses = studentservice.getStudentEnrolledCourses(studentid);
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
