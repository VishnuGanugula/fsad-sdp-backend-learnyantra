package com.klef.fsad.sdp.service;

import java.util.List;
import com.klef.fsad.sdp.entity.Courses;

public interface CourseService {
    // Instructor Actions
    public String addCourse(Courses course);
    public List<Courses> getCoursesByInstructor(int instructorId);
    public String toggleCourseStatus(int id, boolean status);
    
    // Student Actions (Discovery)
    public List<Courses> getAllPublishedCourses();
    public List<Courses> searchCoursesByKeyword(String keyword);
    
    // General Actions
    public List<Courses> viewAllCourses(); // For Admin
    public Courses getCourseById(int id);
    public String deleteCourse(int id);
}