package com.klef.fsad.sdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.fsad.sdp.entity.Instructor;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer>
{
	// SELECT i FROM Instructor i WHERE i.email=?1 AND i.password=?2
    Instructor findByEmailAndPassword(String email, String password);
}