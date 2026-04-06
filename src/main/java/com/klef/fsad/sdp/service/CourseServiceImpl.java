package com.klef.fsad.sdp.service;

import java.util.List;
import java.util.Optional;

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
        return courseRepository.findCoursesByInstructor(instructorId);//it uses  custom @Query to find courses for a specific instructor
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
        return courseRepository.findPublishedCourses(); //it uses @Query to filter only visible courses for students
    }

    @Override
    public List<Courses> searchCoursesByKeyword(String keyword) {
        return courseRepository.searchCourses(keyword);// it uses LIKE %?1% search query
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

        Optional<Courses> optionalCourse = courseRepository.findById(id);

        if (optionalCourse.isPresent()) {
            Courses course = optionalCourse.get();
            courseRepository.delete(course);
            return "Course deleted successfully.";
        } else {
            return "Course not found.";
        }
    }
}