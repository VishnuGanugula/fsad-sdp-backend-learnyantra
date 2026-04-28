package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.dto.PublishedCourseWithInstructorDTO;
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
			return ResponseEntity.ok().body(courses);
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
			return ResponseEntity.ok().body(courses);
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Error Fetching Courses");
		}
	}

	@GetMapping("/published-with-instructor")
	public ResponseEntity<?> viewPublishedCoursesWithInstructor()
	{
		try
		{
			List<PublishedCourseWithInstructorDTO> courses = courseService.getPublishedCoursesWithInstructorDetails();
			return ResponseEntity.ok().body(courses);
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

			
			if(!courses.isEmpty())
			{
				return ResponseEntity.ok().body(courses);
	
				
			}
			else 
			{	
		        return ResponseEntity.status(204).body("No Courses Found");
		     }

		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Error Searching Courses");
		}
	}
	
	@PutMapping("/updatestatus/{id}/{status}")
	public ResponseEntity<String> updateCourseStatus(@PathVariable long id, @PathVariable boolean status)
	{
		try
		{
			String output = courseService.toggleCourseStatus(id, status);
			return ResponseEntity.ok().body(output);
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Error Updating Status");
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCourseById(@PathVariable long id)
	{
		try
		{
			Courses course = courseService.getCourseById(id);
			
			if(course != null)
			{
				return ResponseEntity.ok().body(course);
			}
			else
			{
				return ResponseEntity.status(404).body("Course Not Found");
			}
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Error Fetching Course Details");
		}
	}
}