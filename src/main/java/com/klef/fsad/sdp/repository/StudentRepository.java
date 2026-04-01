package com.klef.fsad.sdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.klef.fsad.sdp.entity.Student;
import jakarta.transaction.Transactional;
import java.util.List;


@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>
{
	// SELECT s FROM Student s WHERE c.email=?1 and s.password=?2
	Student findByEmailAndPassword(String email, String password);
	
	@Query("SELECT s FROM Student s WHERE s.email=?1 and s.password=?2")
	Student checkLogin(String email, String password);
	
	 // ---------------- FIND BY ----------------

    // Derived Method	
	// JPQL: SELECT s FROM student s WHERE s.email=?1;
	Student findByEmail(String email);
	
	@Query("SELECT s FROM Student s WHERE s.email=?1")
	Student getStudentByEmail(String email);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM Student s WHERE s.email=?1")
	int deleteStudentByEmail(String email);
	
	//SELCT s FROM Student s WHERE s.usernamel=?1
	Student findByUsername(String username);
	
	@Query("SELECT s FROM Student s WHERE s.username=?1")
	Student getStudentByUsername(String Username);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM Student s WHERE s.username=?1")
	int deleteStudentByUsername(String username);
	 
	// SELECT s FROM Student s WHERE s.location=?1
	List<Student> findByLocation(String location);
	
	@Query("SELECT s FROM Student s WHERE s.location=?1")
	List<Student> getStudentsByLocation(String location);
	
	//SELECT s FROM Student s WHERE s.gender=?1
	List<Student> findByGender(String gender);
	
	@Query("SELECT s FROM Student s WHERE s.gender=?1")
	List<Student> getStudentByGender(String gender);
	
    // ---------------- SEARCH CUSTOMER BY NAME ----------------

	List<Student> findByFirstNameContaining(String Keyword);
	
	
	@Query("SELECT s FROM Student s WHERE s.firstName LIKE %?1% OR s.lastName LIKE %?1%")
    List<Student> searchStudentByName(String keyword);


    // ---------------- COUNT METHODS ----------------
    
    // Already available from JpaRepository
    // JPQL Equivalent : SELECT COUNT(s) FROM Student s
    long count();

    // Custom JPQL Query without WHERE
    @Query("SELECT COUNT(s) FROM Student s")
    long totalStudents();

    // Derived Method
    // JPQL SELECT COUNT(s) FROM Student s WHERE s.location=?1
    long countByLocation(String location);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.location=?1")
    long totalStudentsByLocation(String location);

    // Derived Method
    // JPQL SELECT : SELECT COUNT(s) FROM Student WHERE s.gender=?1
    long countByGender(String gender);

    // FIX: Added missing entity alias 's' — was causing JPQL parse error
    @Query("SELECT COUNT(s) FROM Student s WHERE s.gender=?1")
    long totalStudentsByGender(String gender);

    // ---------------- EXISTS METHODS ----------------

    // Derived Method
    // JPQL SELECT COUNT(s)>0 FROM Student s WHERE s.email=?1
    boolean existsByEmail(String email);

    @Query("SELECT COUNT(s)>0 FROM Student s WHERE s.email=?1")
    boolean checkEmailExists(String email);
    
    // ---------------- DELETE Query ----------------

    // JPQL DELETE Query
    @Modifying
    @Transactional
    @Query("DELETE FROM Student s WHERE s.location=?1")
    int deleteStudentsByLocation(String location);
   

    // ---------------- UPDATE METHODS ----------------

    // JPQL UPDATE Query
    @Modifying
    @Transactional
    @Query("UPDATE Student s SET s.password=?2 WHERE s.email=?1")
    int updatePasswordByEmail(String email,String password);

    @Modifying
    @Transactional
    @Query("UPDATE Student s SET s.location=?2 WHERE s.username=?1")
    int updateLocationByUsername(String username,String location);
    
    long countByFirstName(String name); //derived method
    
    // findBy, countBy, existBy - Derived Methods
    //update and delete queries
	
}

