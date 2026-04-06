package com.klef.fsad.sdp.service;

import java.util.List;
import com.klef.fsad.sdp.entity.CourseEnrollment;

public interface CourseEnrollmentService {
	public List<CourseEnrollment> getStudentCourses(int studentId);
	public List<CourseEnrollment> getCourseStudents(long courseId);
	public String updateProgress(int studentId, long courseId, int progress);
	public boolean isStudentEnrolled(int studentId, long courseId);
}