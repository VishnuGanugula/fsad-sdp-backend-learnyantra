package com.klef.fsad.sdp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.Admin;
import com.klef.fsad.sdp.entity.Instructor;
import com.klef.fsad.sdp.entity.Student;
import com.klef.fsad.sdp.repository.AdminRepository;
import com.klef.fsad.sdp.repository.InstructorRepository;
import com.klef.fsad.sdp.repository.StudentRepository;

@Service
public class UserServiceImpl implements UserService 
{
    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private InstructorRepository instructorRepo;

    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException 
    {
        // 1. Try Admin - using username
        Optional<Admin> adminOpt = adminRepo.findById(input);
        if (adminOpt.isPresent()) 
        {
            Admin admin = adminOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    admin.getUsername(), 
                    admin.getPassword(),
                    List.of(new SimpleGrantedAuthority("ADMIN"))
            );
        }

        // 2. Try Student - using email or username
        Optional<Student> studentOpt = studentRepo.findByEmail(input);
        if (!studentOpt.isPresent()) {
            studentOpt = studentRepo.findByUsername(input);
        }
        
        if (studentOpt.isPresent()) 
        {
            Student student = studentOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    student.getUsername(), 
                    student.getPassword(),
                    List.of(new SimpleGrantedAuthority("STUDENT"))
            );
        }

        // 3. Try Instructor - using email or username
        Optional<Instructor> instructorOpt = instructorRepo.findByEmail(input);
        if (!instructorOpt.isPresent()) {
            instructorOpt = instructorRepo.findByUsername(input);
        }

        if (instructorOpt.isPresent()) 
        {
            Instructor instructor = instructorOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    instructor.getUsername(),
                    instructor.getPassword(),
                    List.of(new SimpleGrantedAuthority("INSTRUCTOR"))
            );
        }

        throw new UsernameNotFoundException("User not found with input: " + input);
    }

    @Override
    public UserDetails loadUserByUsernameAndRole(String input, String role) {
        if ("ADMIN".equalsIgnoreCase(role)) {
            Optional<Admin> adminOpt = adminRepo.findById(input);
            if (adminOpt.isPresent()) {
                Admin admin = adminOpt.get();
                return new org.springframework.security.core.userdetails.User(
                        admin.getUsername(), admin.getPassword(), List.of(new SimpleGrantedAuthority("ADMIN")));
            }
        } else if ("STUDENT".equalsIgnoreCase(role)) {
            Optional<Student> studentOpt = studentRepo.findByEmail(input);
            if (!studentOpt.isPresent()) studentOpt = studentRepo.findByUsername(input);
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                return new org.springframework.security.core.userdetails.User(
                        student.getUsername(), student.getPassword(), List.of(new SimpleGrantedAuthority("STUDENT")));
            }
        } else if ("INSTRUCTOR".equalsIgnoreCase(role)) {
            Optional<Instructor> instructorOpt = instructorRepo.findByEmail(input);
            if (!instructorOpt.isPresent()) instructorOpt = instructorRepo.findByUsername(input);
            if (instructorOpt.isPresent()) {
                Instructor instructor = instructorOpt.get();
                return new org.springframework.security.core.userdetails.User(
                        instructor.getUsername(), instructor.getPassword(), List.of(new SimpleGrantedAuthority("INSTRUCTOR")));
            }
        }
        throw new UsernameNotFoundException("User not found with input: " + input + " and role: " + role);
    }

	@Override
	public Object getUserByLogin(String input) 
	{
		Optional<Admin> adminOpt = adminRepo.findById(input);
		if (adminOpt.isPresent()) 
		{
			return adminOpt.get();
		}

		Optional<Student> studentOpt = studentRepo.findByEmail(input);
        if (!studentOpt.isPresent()) {
            studentOpt = studentRepo.findByUsername(input);
        }
		if (studentOpt.isPresent()) 
		{
			return studentOpt.get();
		}

		Optional<Instructor> instructorOpt = instructorRepo.findByEmail(input);
        if (!instructorOpt.isPresent()) {
            instructorOpt = instructorRepo.findByUsername(input);
        }
		if (instructorOpt.isPresent()) 
		{
			return instructorOpt.get();
		}

		return null;
	}

    @Override
    public Object getUserByLoginAndRole(String input, String role) {
        if ("ADMIN".equalsIgnoreCase(role)) {
            return adminRepo.findById(input).orElse(null);
        } else if ("STUDENT".equalsIgnoreCase(role)) {
            Optional<Student> opt = studentRepo.findByEmail(input);
            if (!opt.isPresent()) opt = studentRepo.findByUsername(input);
            return opt.orElse(null);
        } else if ("INSTRUCTOR".equalsIgnoreCase(role)) {
            Optional<Instructor> opt = instructorRepo.findByEmail(input);
            if (!opt.isPresent()) opt = instructorRepo.findByUsername(input);
            return opt.orElse(null);
        }
        return null;
    }
}
