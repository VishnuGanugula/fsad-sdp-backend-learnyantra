package com.klef.fsad.sdp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.CourseEnrollment;
import com.klef.fsad.sdp.entity.Courses;
import com.klef.fsad.sdp.entity.Student;
import com.klef.fsad.sdp.entity.Module;
import com.klef.fsad.sdp.entity.Assignment;
import com.klef.fsad.sdp.entity.Submission;
import com.klef.fsad.sdp.repository.StudentRepository;
import com.klef.fsad.sdp.repository.CourseRepository;
import com.klef.fsad.sdp.repository.CourseEnrollmentRepository;
import com.klef.fsad.sdp.repository.ModuleRepository;
import com.klef.fsad.sdp.repository.AssignmentRepository;
import com.klef.fsad.sdp.repository.SubmissionRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class StudentServiceImpl implements StudentService
{

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private CourseEnrollmentRepository courseEnrollmentRepository;

	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private AssignmentRepository assignmentRepository;

	@Autowired
	private SubmissionRepository submissionRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public String studentRegistration(Student student) 
	{
		if (studentRepository.existsByEmail(student.getEmail())) {
			return "Email already exists";
		}
		if (studentRepository.existsByUsername(student.getUsername())) {
			return "Username already exists";
		}
		if (studentRepository.existsByContact(student.getContact())) {
			return "Contact number already exists";
		}
		
		student.setPassword(passwordEncoder.encode(student.getPassword()));
		studentRepository.save(student);
		return "Student Registered Successfully";
	}

	@Override
	public Student verfiyStudentLogin(String email, String pwd)
	{
		return studentRepository.findByEmailAndPassword(email, pwd);
	}

	@Override
	public Student getStudentById(int id)
	{
		return studentRepository.findById(id).orElse(null);
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
			if (student.getPassword() != null && !student.getPassword().isEmpty()) {
				s.setPassword(passwordEncoder.encode(student.getPassword()));
			}
			
			studentRepository.save(s);
			
			return "Student Profile Updated Successfully";
		}
		else
		{
			return "Student ID Not Found to Update";
		}
		
	}

	@Override
	public String enrollInCourse(int studentId, long courseId) 
	{
		try 
		{
			Student student = studentRepository.findById(studentId).orElse(null);
			if(student == null)
			{
				return "Student Not Found";
			}

			Courses course = courseRepository.findById(courseId).orElse(null);
			if(course == null)
			{
				return "Course Not Found";
			}

			// Check if already enrolled
			CourseEnrollment existing = courseEnrollmentRepository.findByStudentIdAndCourseId(studentId, courseId);
			if(existing != null)
			{
				return "Already Enrolled In This Course";
			}

			// Create new enrollment
			CourseEnrollment enrollment = new CourseEnrollment();
			enrollment.setStudent(student);
			enrollment.setCourse(course);
			enrollment.setProgressPercentage(0.0);
			courseEnrollmentRepository.save(enrollment);
			
			return "Enrolled Successfully";
		}
		catch(Exception e)
		{
			return "Error during enrollment: " + e.getMessage();
		}
	}

	@Override
	public String unenrollFromCourse(int studentId, long courseId) 
	{
		try 
		{
			CourseEnrollment enrollment = courseEnrollmentRepository.findByStudentIdAndCourseId(studentId, courseId);
			if(enrollment == null)
			{
				return "Not Enrolled In This Course";
			}
			
			courseEnrollmentRepository.delete(enrollment);
			return "Unenrolled Successfully";
		}
		catch(Exception e)
		{
			return "Error during unenrollment " ;
		}
	}

	@Override
	public List<CourseEnrollment> getStudentEnrollments(int studentId) 
	{
		return courseEnrollmentRepository.findByStudentId(studentId);
	}

	@Override
	public List<Courses> getStudentEnrolledCourses(int studentId) 
	{
		List<CourseEnrollment> enrollments = courseEnrollmentRepository.findByStudentId(studentId);
		return enrollments.stream()
				.map(CourseEnrollment::getCourse)
				.collect(Collectors.toList());
	}

	@Override
	public Student displayStudentById(int id) {
		return studentRepository.findById(id).orElse(null);
	}

	@Override
	public List<Module> getModulesByCourse(long courseId) {
		return moduleRepository.findByCourseIdOrderBySequenceOrderAsc(courseId);
	}

	@Override
	public List<Assignment> getAssignmentsByCourse(long courseId) {
		return assignmentRepository.findByCourseId(courseId);
	}

	@Override
	public String submitAssignment(Submission submission) {
		submissionRepository.save(submission);
		return "Assignment Submitted Successfully";
	}

	@Override
	public Submission getSubmissionStatus(long assignmentId, int studentId) {
		return submissionRepository.findByAssignmentIdAndStudentId(assignmentId, studentId);
	}

}
