package com.klef.fsad.sdp.service;

import com.klef.fsad.sdp.entity.Student;

public  interface StudentService 
{	
	public String studentRegistration(Student student);
	public Student verfiyStudentLogin(String email, String pwd);
	public String updateStudentProfile(Student student);
	
	
}
