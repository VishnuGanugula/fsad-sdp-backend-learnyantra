package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.entity.Courses;
import com.klef.fsad.sdp.service.InstructorService;

@RestController
@RequestMapping("courseapi")
@CrossOrigin("*")
public class CourseController 
{
	@Autowired
	private InstructorService instructorService;
	
	@GetMapping("/")
	public String coursehome()
	{
		return "Course Controller Demo";
	}

	@GetMapping("/all")
	public ResponseEntity<?> viewAllCourses()
	{
		try
		{
			List<Courses> courses = instructorService.viewAllCourses();
			if(courses == null || courses.isEmpty())
			{
				return ResponseEntity.status(204).body("No Courses Found");
			}
			return ResponseEntity.ok(courses);
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Error Fetching Courses");
		}
	}
	
	@GetMapping("/published")
	public ResponseEntity<?> viewPublishedCourses()
	{
		try
		{
			List<Courses> courses = instructorService.viewPublishedCourses();
			
			if(courses == null || courses.isEmpty())
			{
				return ResponseEntity.status(204).body("No Courses Found");
			}
			
			return ResponseEntity.ok(courses);
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Error Fetching Courses");
		}
	}
	
	@GetMapping("/search/{keyword}")
	public ResponseEntity<?> searchCourses(@PathVariable String keyword)
	{
		try
		{
			List<Courses> courses = instructorService.searchCourses(keyword);
			
			if(courses == null || courses.isEmpty())
			{
				return ResponseEntity.status(204).body("No Courses Found");
			}
			
			return ResponseEntity.ok(courses);
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Error Searching Courses");
		}
	}
	
	@PutMapping("/updatestatus/{id}/{status}")
	public ResponseEntity<String> updateCourseStatus(@PathVariable int id, @PathVariable boolean status)
	{
		try
		{
			String output = instructorService.updateCourseStatus(id, status);
			return ResponseEntity.ok(output);
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Error Updating Status");
		}
	}
}