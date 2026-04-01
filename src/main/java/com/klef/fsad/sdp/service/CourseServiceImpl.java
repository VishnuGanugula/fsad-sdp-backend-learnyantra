package com.klef.fsad.sdp.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.klef.fsad.sdp.entity.Courses;
import com.klef.fsad.sdp.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public String addCourse(Courses course) {
        courseRepository.save(course);
        return "Course added successfully!";
    }

    @Override
    public List<Courses> getCoursesByInstructor(int instructorId) {
        // Uses your custom @Query to find courses for a specific instructor
        return courseRepository.findCoursesByInstructor(instructorId);
    }

    @Override
    public String toggleCourseStatus(int id, boolean status) {
        // Uses your @Modifying @Query to update publication status
        int rows = courseRepository.updateCourseStatus(id, status);
        if (rows > 0) {
            return "Course status updated to " + (status ? "Published" : "Draft");
        }
        return "Course ID not found.";
    }

    @Override
    public List<Courses> getAllPublishedCourses() {
        // Uses your @Query to filter only visible courses for students
        return courseRepository.findPublishedCourses();
    }

    @Override
    public List<Courses> searchCoursesByKeyword(String keyword) {
        // Uses your LIKE %?1% search query
        return courseRepository.searchCourses(keyword);
    }

    @Override
    public List<Courses> viewAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Courses getCourseById(int id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public String deleteCourse(int id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return "Course deleted successfully.";
        }
        return "Course not found.";
    }
}