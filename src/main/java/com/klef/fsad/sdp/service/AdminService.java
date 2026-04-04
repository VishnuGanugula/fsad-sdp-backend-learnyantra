package com.klef.fsad.sdp.service;

import java.util.List;

import com.klef.fsad.sdp.dto.StudentDTO;
import com.klef.fsad.sdp.entity.Admin;
import com.klef.fsad.sdp.entity.Instructor;
import com.klef.fsad.sdp.entity.Student;

public interface AdminService {
	
	public Admin verifyAdminLogin(String username,String password);
	//Instructor
	public String addInstructor(Instructor instructor);
    public List<Instructor> viewAllInstructors();
    public boolean deleteInstructor(int id); 
    
    //Student Management
    public List<Student> viewAllStudents();
    public String deleteStudent(int id); 
    public StudentDTO studentToStudentDTO(Student student);
    public List<StudentDTO> displayAllStudentsDTO();
    //Dashboard
    public long getStudentCount();
    public long getInstructorCount();
}
