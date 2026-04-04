package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.dto.InstructorDTO;
import com.klef.fsad.sdp.entity.Courses;
import com.klef.fsad.sdp.entity.Instructor;
import com.klef.fsad.sdp.service.InstructorService;

@RestController
@RequestMapping("instructorapi")
@CrossOrigin("*")
public class InstructorController 
{
  @Autowired
  private InstructorService instructorService;
  
  @GetMapping("/")
  public String instructorhome()
  {
	   return "Instructor Controller Demo";
  }
  
  @PostMapping("login")
  public ResponseEntity<?> verifyinstructorlogin(@RequestBody Instructor instructor)
  {
	   try
		{
			InstructorDTO ins = instructorService.verifyInstructorLogin(instructor.getEmail(), instructor.getPassword());
		
		    if(ins!=null)
		    {
		    	return ResponseEntity.status(200).body(ins);
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
  
  @GetMapping("/courses/{instructorid}")
  public ResponseEntity<?> viewCoursesByInstructor(@PathVariable int instructorid)
  {
	  try
	  {
		  List<Courses> courses = instructorService.viewCourseDetailsByInstructor(instructorid);
		  if(courses == null || courses.isEmpty())
		  {
			  return ResponseEntity.status(204).body("No Courses Found");
		  }
		  return ResponseEntity.status(200).body(courses);
	  }
	  catch (Exception e)
	  {
		  return ResponseEntity.status(500).body("Internal Server Error");
	  }
  }

  @PostMapping("/addcourse")
  public ResponseEntity<String> addCourse(@RequestBody Courses course)
  {
	  try
	  {
		  String output = instructorService.addCourse(course);
		  return ResponseEntity.status(201).body(output);
	  }
	  catch (Exception e)
	  {
		  return ResponseEntity.status(500).body("Internal Server Error");
	  }
  }
  
  @GetMapping("/course/{courseid}/students")
  public ResponseEntity<?> getEnrolledStudents(@PathVariable long courseid)
  {
	  try
	  {
		  List<?> enrollments = instructorService.viewStudentsRegisteredInCourse((int) courseid);
		  if(enrollments == null || enrollments.isEmpty())
		  {
			  return ResponseEntity.status(204).body("No Students Enrolled");
		  }
		  return ResponseEntity.status(200).body(enrollments);
	  }
	  catch (Exception e)
	  {
		  return ResponseEntity.status(500).body("Internal Server Error");
	  }
  }
  
  @DeleteMapping("/deletecourse/{courseid}")
  public ResponseEntity<String> deleteCourse(@PathVariable int courseid)
  {
	  try
	  {
		  String output = instructorService.deleteCourse(courseid);
		  if(output.contains("Successfully"))
		  {
			  return ResponseEntity.status(200).body(output);
		  }
		  return ResponseEntity.status(404).body(output);
	  }
	  catch (Exception e)
	  {
		  return ResponseEntity.status(500).body("Internal Server Error");
	  }
  }
}