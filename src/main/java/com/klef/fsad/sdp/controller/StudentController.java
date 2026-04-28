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

import com.klef.fsad.sdp.dto.EmailDTO;
import com.klef.fsad.sdp.entity.CourseEnrollment;
import com.klef.fsad.sdp.entity.Courses;
import com.klef.fsad.sdp.entity.Student;
import com.klef.fsad.sdp.service.StudentService;
import com.klef.fsad.sdp.service.EmailService;

@RestController
@RequestMapping("studentapi")
@CrossOrigin("*")
public class StudentController 
{
	
	@Autowired
	private StudentService studentservice;

	@Autowired
	private EmailService emailService;
	
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
			if (output.contains("Successfully")) {
				EmailDTO emailDTO = new EmailDTO();
				emailDTO.setTo(s.getEmail());
				emailDTO.setSubject("Welcome to Learn Yantra");
				emailDTO.setText(
						"Dear " + s.getFirstName() + " " + s.getLastName() + ",\n\n" +
						"Thanks for joining us at Learn Yantra! We're happy to have you on board.\n\n" +
						"Your student account has been created successfully. You can now log in, explore courses, and start learning at your own pace.\n\n" +
						"If you need any help, our team is here for you.\n\n" +
						"Warm regards,\n" +
						"Learn Yantra Team");

				try {
					emailService.sendEmail(emailDTO);
				} catch (Exception emailException) {
					System.out.println("Student created but welcome email sending failed for: " + s.getEmail());
				}
				return ResponseEntity.status(201).body(output);
			} else {
				return ResponseEntity.status(400).body(output);
			}
		}
		catch (Exception e) 
		{
			return ResponseEntity.status(500).body("Internal Server Error");
		}
		
	}
	
	// Legacy login removed. Use /auth/login for JWT authentication.

	
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
	
	  @GetMapping("/display/{id}")
		public ResponseEntity<?> displaystudentbyId(@PathVariable int id)
		{
			Student s = studentservice.displayStudentById(id);
			if(s != null)
			{
				//return ResponseEntity.ok().body(u.toString()); // object will be display in string format
				return ResponseEntity.ok().body(s);
			}
			else
			{
				return ResponseEntity.ok().body("User Id Not Found"); // string type
			}
		}
		
}