package com.klef.fsad.sdp.service;
import com.klef.fsad.sdp.entity.CourseEnrollment;
import java.util.*;
public interface CourseEnrollmentService {

    String enrollStudent(int studentId, long courseId);

    List<CourseEnrollment> getStudentCourses(int studentId);

    List<CourseEnrollment> getCourseStudents(long courseId);

    String updateProgress(int studentId, long courseId, int progress);

    boolean isStudentEnrolled(int studentId, long courseId);
}