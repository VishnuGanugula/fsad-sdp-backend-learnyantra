package com.klef.fsad.sdp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.dto.InstructorDTO;
import com.klef.fsad.sdp.dto.StudentDTO;
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
	public List<Student> viewAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public String deleteStudent(int id) {
Optional<Student> optional = studentRepository.findById(id);
		
		if(optional.isPresent())
		{
			studentRepository.deleteById(id);
			return "Student Deleted Successfully";
		}
		else
		{
			return "Student ID Not Found to Delete";
		}
	}

	@Override
	public long getStudentCount() {
		return studentRepository.count();
	}

	@Override
	public long getInstructorCount() {
		return instructorRepository.count();
	}

	@Override
	public StudentDTO studentToStudentDTO(Student student) {
		StudentDTO dto = new StudentDTO();
		dto.setId(student.getId());
		dto.setFirstName(student.getFirstName());
		dto.setLastName(student.getLastName());
		dto.setGender(student.getGender());
		dto.setLocation(student.getLocation());
		return dto;
	}

	@Override
	public List<StudentDTO> displayAllStudentsDTO() {
		List<Student> students = viewAllStudents();
		return students.stream()
				.map(this::studentToStudentDTO)
				.collect(Collectors.toList());
	}

	@Override
	public boolean deleteInstructor(int  id)
	{
		 Optional<Instructor> optionalInstructor = instructorRepository.findById(id);

	        if (optionalInstructor.isPresent()) {
	            instructorRepository.delete(optionalInstructor.get());
	            return true;
	        } 
	        else
	        {	        	
	            return false;
	        }
	}
}

	


