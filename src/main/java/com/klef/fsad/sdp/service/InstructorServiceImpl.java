package com.klef.fsad.sdp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.Courses;
import com.klef.fsad.sdp.entity.Instructor;
import com.klef.fsad.sdp.entity.Student;
import com.klef.fsad.sdp.entity.CourseEnrollment;
import com.klef.fsad.sdp.entity.Module;
import com.klef.fsad.sdp.entity.Assignment;
import com.klef.fsad.sdp.entity.Submission;
import com.klef.fsad.sdp.repository.CourseRepository;
import com.klef.fsad.sdp.repository.InstructorRepository;
import com.klef.fsad.sdp.repository.CourseEnrollmentRepository;
import com.klef.fsad.sdp.repository.ModuleRepository;
import com.klef.fsad.sdp.repository.AssignmentRepository;
import com.klef.fsad.sdp.repository.SubmissionRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class InstructorServiceImpl implements InstructorService
{
	@Autowired
	private InstructorRepository instructorRepository;
	
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
	public Instructor verifyInstructorLogin(String email, String pwd) 
	{
		return instructorRepository.findByEmailAndPassword(email, pwd);
	}

	@Override
	public String addCourse(Courses course) {
		courseRepository.save(course);
		return "Course Added Successfully";
	}

	@Override
	public List<Courses> viewCourseDetailsByInstructor(int instructorid) {
		return courseRepository.findCoursesByInstructor(instructorid);
	}

	@Override
	public String deleteCourse(int courseid) {
		courseRepository.deleteById((long) courseid);
		return "Course Deleted Successfully";
	}

	@Override
	public List<Student> viewStudentsRegisteredInCourse(int courseid) {
		List<CourseEnrollment> enrollments = courseEnrollmentRepository.findByCourseId(courseid);
		if(enrollments != null && !enrollments.isEmpty()) {
			return enrollments.stream()
					.map(enrollment -> enrollment.getStudent())
					.collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public String addModule(Module module) {
		moduleRepository.save(module);
		return "Module Added Successfully";
	}

	@Override
	public List<Module> getModulesByCourse(long courseId) {
		return moduleRepository.findByCourseIdOrderBySequenceOrderAsc(courseId);
	}

	@Override
	public String addAssignment(Assignment assignment) {
		assignmentRepository.save(assignment);
		return "Assignment Added Successfully";
	}

	@Override
	public List<Assignment> getAssignmentsByCourse(long courseId) {
		return assignmentRepository.findByCourseId(courseId);
	}

	@Override
	public List<Submission> getSubmissionsByAssignment(long assignmentId) {
		return submissionRepository.findByAssignmentId(assignmentId);
	}

	@Override
	public String gradeSubmission(long submissionId, double grade, String feedback) {
		Submission submission = submissionRepository.findById(submissionId).orElse(null);
		if (submission != null) {
			submission.setGrade(grade);
			submission.setFeedback(feedback);
			submissionRepository.save(submission);
			return "Submission Graded Successfully";
		}
		return "Submission Not Found";
	}
}