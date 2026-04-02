package com.klef.fsad.sdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.klef.fsad.sdp.entity.CourseEnrollment;

@Repository
public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, Long>
{
	//SELECT ce FROM CourseEnrollment ce WHERE ce.course.id = ?1
	List<CourseEnrollment> findByCourseId(long courseId);
	
	//SELECT ce FROM CourseEnrollment ce WHERE ce.student.id = ?1
	List<CourseEnrollment> findByStudentId(int studentId);
	
	//SELECT ce FROM CourseEnrollment ce WHERE ce.student.id = ?1 AND ce.course.id = ?2
	CourseEnrollment findByStudentIdAndCourseId(int studentId, long courseId);
	
}
