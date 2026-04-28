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
import com.klef.fsad.sdp.entity.CourseEnrollment;
import com.klef.fsad.sdp.entity.Student;
import com.klef.fsad.sdp.repository.AdminRepository;
import com.klef.fsad.sdp.repository.InstructorRepository;
import com.klef.fsad.sdp.repository.CourseEnrollmentRepository;
import com.klef.fsad.sdp.repository.StudentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private InstructorRepository instructorRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseEnrollmentRepository courseEnrollmentRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Admin verifyAdminLogin(String username, String password) {
		return adminRepository.findByUsernameAndPassword(username, password);

	}

	@Override
	public String addInstructor(Instructor instructor) {
		instructor.setPassword(passwordEncoder.encode(instructor.getPassword()));
		instructorRepository.save(instructor);
		return "Instructor Added Successfully";
	}

	@Override
	public List<Instructor> viewAllInstructors() {
		return instructorRepository.findAll();
	}

	@Override
	public List<StudentDTO> viewAllStudents() {
		return studentRepository.findAll().stream()
				.map(this::studentToStudentDTO)
				.collect(Collectors.toList());
	}

	@Override
	public String deleteStudent(int id) {
		Optional<Student> optional = studentRepository.findById(id);

		if (optional.isPresent()) {
			studentRepository.deleteById(id);
			return "Student Deleted Successfully";
		} else {
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
		dto.setUsername(student.getUsername());
		dto.setEmail(student.getEmail());
		dto.setFirstName(student.getFirstName());
		dto.setLastName(student.getLastName());
		dto.setContact(student.getContact());
		dto.setGender(student.getGender());
		dto.setLocation(student.getLocation());

		List<CourseEnrollment> enrollments = courseEnrollmentRepository.findByStudentId(student.getId());
		if (enrollments == null || enrollments.isEmpty()) {
			dto.setCourseTitle("Not Enrolled Yet");
		} else {
			String courseTitles = enrollments.stream()
					.map(enrollment -> enrollment.getCourse() != null ? enrollment.getCourse().getTitle() : null)
					.filter(title -> title != null && !title.isBlank())
					.distinct()
					.reduce((left, right) -> left + ", " + right)
					.orElse("Not Enrolled Yet");
			dto.setCourseTitle(courseTitles);
		}
		return dto;
	}

	@Override
	public List<StudentDTO> displayAllStudentsDTO() {
		return viewAllStudents();
	}

	@Override
	public boolean deleteInstructor(int id) {
		Optional<Instructor> optionalInstructor = instructorRepository.findById(id);

		if (optionalInstructor.isPresent()) {
			instructorRepository.delete(optionalInstructor.get());
			return true;
		} else {
			return false;
		}
	}
}
