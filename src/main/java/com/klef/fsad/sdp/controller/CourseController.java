package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.entity.Courses;
import com.klef.fsad.sdp.service.CourseService;

@RestController
@RequestMapping("courseapi")
@CrossOrigin("*")
public class CourseController 
{
	@Autowired
	private CourseService courseService;
	
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
			List<Courses> courses = courseService.viewAllCourses();
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
			List<Courses> courses = courseService.getAllPublishedCourses();
			
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
			List<Courses> courses = courseService.searchCoursesByKeyword(keyword);
			
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
			String output = courseService.toggleCourseStatus(id, status);
			return ResponseEntity.ok(output);
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Error Updating Status");
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCourseById(@PathVariable int id)
	{
		try
		{
			Courses course = courseService.getCourseById(id);
			if(course != null)
			{
				return ResponseEntity.ok(course);
			}
			return ResponseEntity.status(404).body("Course Not Found");
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Error Fetching Course Details");
		}
	}
}