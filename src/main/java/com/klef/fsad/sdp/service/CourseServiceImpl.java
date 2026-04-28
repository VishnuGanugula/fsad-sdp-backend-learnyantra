package com.klef.fsad.sdp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.dto.InstructorDTO;
import com.klef.fsad.sdp.dto.PublishedCourseWithInstructorDTO;
import com.klef.fsad.sdp.entity.Courses;
import com.klef.fsad.sdp.entity.Instructor;
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
    public String toggleCourseStatus(long id, boolean status) {
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
    public List<PublishedCourseWithInstructorDTO> getPublishedCoursesWithInstructorDetails() {
        return courseRepository.findPublishedCourses().stream().map(course -> {
            PublishedCourseWithInstructorDTO dto = new PublishedCourseWithInstructorDTO();
            dto.setId(course.getId());
            dto.setTitle(course.getTitle());
            dto.setDescription(course.getDescription());
            dto.setCategory(course.getCategory());
            dto.setPublished(course.isPublished());
            dto.setInstructor(mapInstructorToDTO(course.getInstructor()));
            return dto;
        }).collect(Collectors.toList());
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
    public Courses getCourseById(long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public String deleteCourse(long id) {

        Optional<Courses> optionalCourse = courseRepository.findById(id);

        if (optionalCourse.isPresent()) {
            Courses course = optionalCourse.get();
            courseRepository.delete(course);
            return "Course deleted successfully.";
        } else {
            return "Course not found.";
        }
    }

    private InstructorDTO mapInstructorToDTO(Instructor instructor) {
        if (instructor == null) {
            return null;
        }

        InstructorDTO instructorDTO = new InstructorDTO();
        instructorDTO.setId(instructor.getId());
        instructorDTO.setEmail(instructor.getEmail());
        instructorDTO.setFirstName(instructor.getFirstName());
        instructorDTO.setLastName(instructor.getLastName());
        return instructorDTO;
    }
}