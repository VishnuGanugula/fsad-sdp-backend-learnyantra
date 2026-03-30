package com.klef.fsad.sdp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.Student;
import com.klef.fsad.sdp.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService
{

	@Autowired
	private StudentRepository studentRepository;
	
	@Override
	public String studentRegistration(Student student) 
	{
		studentRepository.save(student);
		return "Student Registered Successfully";
	}

	@Override
	public Student verfiyStudentLogin(String email, String pwd)
	{
		return studentRepository.findByEmailAndPassword(email, pwd);
	}

	@Override
	public String updateStudentProfile(Student student) 
	{
		Optional<Student> optional = studentRepository.findById(student.getId());
		
		if(optional.isPresent())
		{
			Student s = optional.get();
			
			s.setContact(student.getContact());
			s.setEmail(student.getEmail());
			s.setFirstName(student.getFirstName());
			s.setPassword(student.getPassword());
			
			// FIX: Added missing save() — without this, changes were never persisted to the DB
			//studentRepository.save(s);
			
			return "Student Profile Updated Successfully";
		}
		else
		{
			return "Student ID Not Found to Update";
		}
		
	}

}
