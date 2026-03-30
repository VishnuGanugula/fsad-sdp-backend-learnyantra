package com.klef.fsad.sdp.service;

import com.klef.fsad.sdp.entity.Instructor;


public interface InstructorService 
{
	public Instructor verifyInstructorLogin(String email,String pwd);
    
}