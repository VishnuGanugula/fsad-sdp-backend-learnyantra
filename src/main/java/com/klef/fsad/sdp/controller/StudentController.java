package com.klef.fsad.sdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	
	@PostMapping("/login")
	public ResponseEntity<?> verifystudentlogin(@RequestBody Student student)
	{
		try 
		{
			Student s = studentservice.verfiyStudentLogin(student.getEmail(), student.getPassword());
			
			if(s != null)
			{
				return ResponseEntity.ok().body(s);
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

	@GetMapping("/getprofile/{id}")
	public ResponseEntity<?> getStudentProfile(@PathVariable int id)
	{
		try
		{
			Student student = studentservice.getStudentById(id);
			
			if(student != null)
			{
				return ResponseEntity.ok().body(student);
			}
			else
			{
				return ResponseEntity.status(404).body("Student Not Found");
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
			return ResponseEntity.ok().body(output);
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}
}