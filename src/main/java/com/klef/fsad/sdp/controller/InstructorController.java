package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.entity.Courses;
import com.klef.fsad.sdp.entity.Instructor;
import com.klef.fsad.sdp.entity.Student;
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
  
  // Legacy login removed. Use /auth/login for JWT authentication.

  
@PostMapping("/addcourse")
public ResponseEntity<String> addCourse(@RequestBody Courses course)
{
   try
   {
       String output = instructorService.addCourse(course);
       return ResponseEntity.status(201).body(output);
   }
   catch(Exception e)
   {
       return ResponseEntity.status(500).body("Error Adding Course");
   }
}
  
@GetMapping("/viewmycourses/{instructorid}")
public ResponseEntity<?> viewMyCourses(@PathVariable int instructorid)
{
    try
    {
        List<Courses> mycourses = instructorService.viewCourseDetailsByInstructor(instructorid);

        if(mycourses == null || mycourses.isEmpty())
        {
            return ResponseEntity.status(204).body("No Courses Found");
        }

        return ResponseEntity.ok(mycourses);
    }
    catch(Exception e)
    {
        return ResponseEntity.status(500).body("Error Fetching Courses");
    }
}

@DeleteMapping("/deletecourse/{id}")
public ResponseEntity<String> deleteCourse(@PathVariable int id)
{
 try
 {
     String output = instructorService.deleteCourse(id);
     return ResponseEntity.ok(output);
 }
 catch(Exception e)
 {
     return ResponseEntity.status(500).body("Error Deleting Course");
 }
}

@GetMapping("/studentsbycourse/{courseId}")
public ResponseEntity<?> getStudentsByCourse(@PathVariable int courseId) 
{
    try 
    {
        List<Student> students = instructorService.viewStudentsRegisteredInCourse(courseId);

        if(students == null || students.isEmpty())
        {
            return ResponseEntity.status(204).body("No Students Found");
        }

        return ResponseEntity.ok(students);

    } 
    catch (Exception e) 
    {
    	return ResponseEntity.status(500).body("Internal Server Error");
    }
}
}