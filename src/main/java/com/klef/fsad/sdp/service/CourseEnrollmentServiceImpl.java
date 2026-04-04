package com.klef.fsad.sdp.service;
	import java.util.List;
	import java.util.Optional;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;
	import com.klef.fsad.sdp.entity.CourseEnrollment;
	import com.klef.fsad.sdp.entity.Student;
	import com.klef.fsad.sdp.entity.Courses;
	import com.klef.fsad.sdp.repository.CourseEnrollmentRepository;
	import com.klef.fsad.sdp.repository.StudentRepository;
	import com.klef.fsad.sdp.repository.CourseRepository;
	
	
	@Service
	public class CourseEnrollmentServiceImpl implements CourseEnrollmentService {

	    @Autowired
	    private CourseEnrollmentRepository enrollmentRepo;

	    @Autowired
	    private StudentRepository studentRepo;

	    @Autowired
	    private CourseRepository courseRepo;

	    @Override
	    public String enrollStudent(int studentId, long courseId) {

	        CourseEnrollment existing =
	                enrollmentRepo.findByStudentIdAndCourseId(studentId, courseId);

	        if (existing != null) {
	            return "Student already enrolled!";
	        }

	        Optional<Student> studentOpt = studentRepo.findById(studentId);
	        Optional<Courses> courseOpt = courseRepo.findById((int)courseId);

	        if (studentOpt.isPresent() && courseOpt.isPresent()) {

	            CourseEnrollment enrollment = new CourseEnrollment();
	            enrollment.setStudent(studentOpt.get());
	            enrollment.setCourse(courseOpt.get());
	            enrollment.setProgress(0);

	            enrollmentRepo.save(enrollment);

	            return "Enrollment successful!";
	        } else {
	            return "Student or Course not found!";
	        }
	    }

	    @Override
	    public List<CourseEnrollment> getStudentCourses(int studentId) {
	        return enrollmentRepo.findByStudentId(studentId);
	    }

	    @Override
	    public List<CourseEnrollment> getCourseStudents(long courseId) {
	        return enrollmentRepo.findByCourseId(courseId);
	    }

	    @Override
	    public String updateProgress(int studentId, long courseId, int progress) {

	        CourseEnrollment enrollment =
	                enrollmentRepo.findByStudentIdAndCourseId(studentId, courseId);

	        if (enrollment != null) {
	            enrollment.setProgress(progress);
	            enrollmentRepo.save(enrollment);
	            return "Progress updated!";
	        } else {
	            return "Enrollment not found!";
	        }
	    }

	    @Override
	    public boolean isStudentEnrolled(int studentId, long courseId) {
	        return enrollmentRepo
	                .findByStudentIdAndCourseId(studentId, courseId) != null; // if the student is registered it returns the true value which mean it is not null
	    }
	}