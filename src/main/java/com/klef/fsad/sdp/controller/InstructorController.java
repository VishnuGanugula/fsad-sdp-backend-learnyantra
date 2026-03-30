package com.klef.fsad.sdp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
			Instructor ins = instructorService.verifyInstructorLogin(instructor.getEmail(), instructor.getPassword());
		
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
  
}