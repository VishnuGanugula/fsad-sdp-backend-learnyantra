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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klef.fsad.sdp.dto.StudentDTO;
import com.klef.fsad.sdp.entity.Admin;
import com.klef.fsad.sdp.entity.Instructor;
import com.klef.fsad.sdp.entity.Student;
import com.klef.fsad.sdp.service.AdminService;

@RestController
@RequestMapping("adminapi")
@CrossOrigin("*")
public class AdminController 
{
	@Autowired
	private AdminService adminService;
	
	@GetMapping("/")
	public String home()
	{
		return "LMS Backend Project";
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> checkadminlogin(@RequestBody Admin admin)
	{
		try
		{
			Admin a = adminService.verifyAdminLogin(admin.getUsername(), admin.getPassword());
		
		    if(a != null)
		    {
		    	return ResponseEntity.status(200).body(a);
		    }
		    else
		    {
		    	return ResponseEntity.status(401).body("Login Invalid");
		    }
		}
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}
	
	@PostMapping("/addinstructor")
	public ResponseEntity<String> addinstructor(@RequestBody Instructor instructor)
	{
		   try
		   {
			   String output = adminService.addInstructor(instructor);
			   return ResponseEntity.status(201).body(output);
		   }
		   catch(Exception e)
		   {
			   return ResponseEntity.status(500).body("Error adding instructor: " + e.getMessage());
		   }
	}
	@GetMapping("/viewallinstructors")
	public ResponseEntity<?> viewallinstructors()
	{
	    try
	    {
	        List<Instructor> instructors = adminService.viewAllInstructors();
	        return ResponseEntity.ok(instructors);
	    }
	    catch(Exception e)
	    {
	        return ResponseEntity.status(500).body("Error Fetching Instructors");
	    }
	}
	@DeleteMapping("/deleteinstructor/{id}")
	public ResponseEntity<String> deleteInstructor(@PathVariable int id) {
	    boolean deleted = adminService.deleteInstructor(id);

	    if (deleted) {
	        return ResponseEntity.ok("Instructor Deleted Successfully");
	    } else {
	        return ResponseEntity.status(404).body("Instructor Not Found");
	    }
	}
	@GetMapping("/viewallstudents")
	public ResponseEntity<?> viewAllStudents()
	{
	    try
	    {
	        List<Student> students = adminService.viewAllStudents();
	        return ResponseEntity.ok(students);
	    }
	    catch(Exception e)
	    {
	        return ResponseEntity.status(500).body("Error Fetching Students");
	    }
	}

	@GetMapping("/displayallstudentsdto")
	public ResponseEntity<?> displayAllStudentsDTO()
	{
		try
		{
			List<StudentDTO> students = adminService.displayAllStudentsDTO();
			return ResponseEntity.ok(students);
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Error Fetching Students");
		}
	}
	@DeleteMapping("/deletestudent")
	public ResponseEntity<String> deletestudent(@RequestParam int id)
	{
	      try
	      {
	          String output = adminService.deleteStudent(id);
	          return ResponseEntity.status(200).body(output);
	      }
	      catch(Exception e)
	      {
	          return ResponseEntity.status(500).body("Internal Server Error");
	      }
	}

	@GetMapping("/studentcount")
	public ResponseEntity<?> getStudentCount() 
	{
		try 
		{
			long studentCount = adminService.getStudentCount();
			return ResponseEntity.ok("Students: " + studentCount);
		} 
		catch (Exception e) 
		{
			return ResponseEntity.status(500).body("Error fetching student count");
		}
	}
	@GetMapping("/instructorcount")
	public ResponseEntity<?> getInstructorCount() 
	{
		try 
		{
			long instructorCount = adminService.getInstructorCount();
			return ResponseEntity.ok("Instructors: " + instructorCount);
		} 
		catch (Exception e) 
		{
			return ResponseEntity.status(500).body("Error fetching instructor count");
		}
	}
	

}