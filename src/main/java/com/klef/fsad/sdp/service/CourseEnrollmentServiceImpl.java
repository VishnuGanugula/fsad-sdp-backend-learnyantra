package com.klef.fsad.sdp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.CourseEnrollment;
import com.klef.fsad.sdp.entity.Student;
import com.klef.fsad.sdp.entity.Courses;
import com.klef.fsad.sdp.repository.CourseEnrollmentRepository;
import com.klef.fsad.sdp.repository.StudentRepository;
import com.klef.fsad.sdp.repository.CourseRepository;

@Service
public class CourseEnrollmentServiceImpl implements CourseEnrollmentService {

	@Autowired
	private CourseEnrollmentRepository repository;

	@Autowired
	private StudentRepository studentrepository;

	@Autowired
	private CourseRepository courserepository;


	@Override
	public List<CourseEnrollment> getStudentCourses(int studentId) {
		return repository.findByStudentId(studentId);
	}

	@Override
	public List<CourseEnrollment> getCourseStudents(long courseId) {
		return repository.findByCourseId(courseId);
	}

	@Override
	public String updateProgress(int studentId, long courseId, int progress) {

		CourseEnrollment ce = repository.findByStudentIdAndCourseId(studentId, courseId);

		if(ce != null) {
			ce.setProgress(progress);
			repository.save(ce);
			return "Updated Successfully";
		}else {
			return "Not found";
		}
	}

	@Override
	public boolean isStudentEnrolled(int studentId, long courseId) {

		CourseEnrollment ce = repository.findByStudentIdAndCourseId(studentId, courseId);

		if(ce != null) {
			return true;
		}else {
			return false;
		}
	}
}