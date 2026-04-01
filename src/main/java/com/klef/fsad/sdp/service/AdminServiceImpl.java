package com.klef.fsad.sdp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.Admin;
import com.klef.fsad.sdp.entity.Instructor;
import com.klef.fsad.sdp.entity.Student;
import com.klef.fsad.sdp.repository.AdminRepository;
import com.klef.fsad.sdp.repository.InstructorRepository;
import com.klef.fsad.sdp.repository.StudentRepository;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private InstructorRepository instructorRepository;
	
	@Autowired
	private StudentRepository studentRepository;

	@Override
	public Admin verifyAdminLogin(String username, String password) {
		return adminRepository.findByUsernameAndPassword(username, password);
		
	}

	@Override
	public String addInstructor(Instructor instructor) {
		instructorRepository.save(instructor);
		return "Instructor Added Successfully";
	}

	@Override
	public List<Instructor> viewAllInstructors() {
		return instructorRepository.findAll();
	}

	@Override
	public boolean deleteInstructor(int id) {
		if(instructorRepository.existsById(id))
		{
			instructorRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public List<Student> viewAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public String deleteStudent(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getStudentCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getInstructorCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
