package com.klef.fsad.sdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.Instructor;
import com.klef.fsad.sdp.repository.InstructorRepository;

@Service
public class InstructorServiceImpl implements InstructorService
{
	@Autowired
	private InstructorRepository instructorRepository;

	@Override
	public Instructor verifyInstructorLogin(String email, String pwd) 
	{
		return instructorRepository.findByEmailAndPassword(email, pwd);
	}
}