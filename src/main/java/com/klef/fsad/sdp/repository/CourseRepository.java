package com.klef.fsad.sdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.klef.fsad.sdp.entity.Courses;

import jakarta.transaction.Transactional;

@Repository
public interface CourseRepository extends JpaRepository<Courses, Long> {
    
    @Query("SELECT c FROM Courses c WHERE c.instructor.id = ?1")
    List<Courses> findCoursesByInstructor(int instructorId);
    


    @Query("SELECT c FROM Courses c WHERE c.isPublished = true")
    List<Courses> findPublishedCourses();

    
    @Query("SELECT c FROM Courses c WHERE c.title LIKE %?1% OR c.description LIKE %?1%")
    List<Courses> searchCourses(String keyword);

    @Modifying
    @Transactional
    @Query("UPDATE Courses c SET c.isPublished=?2 WHERE c.id=?1")
    int updateCourseStatus(long id, boolean status);
}
