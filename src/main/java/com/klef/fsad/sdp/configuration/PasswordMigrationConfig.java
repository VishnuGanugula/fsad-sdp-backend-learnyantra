package com.klef.fsad.sdp.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.klef.fsad.sdp.entity.Admin;
import com.klef.fsad.sdp.entity.Instructor;
import com.klef.fsad.sdp.entity.Student;
import com.klef.fsad.sdp.repository.AdminRepository;
import com.klef.fsad.sdp.repository.InstructorRepository;
import com.klef.fsad.sdp.repository.StudentRepository;

import java.util.List;

@Configuration
public class PasswordMigrationConfig {

    @Bean
    public CommandLineRunner migratePasswords(
            AdminRepository adminRepo,
            StudentRepository studentRepo,
            InstructorRepository instructorRepo,
            PasswordEncoder passwordEncoder) {
        
        return args -> {
            System.out.println("Starting Password Migration Check...");

            // 1. Migrate Admins
            List<Admin> admins = adminRepo.findAll();
            for (Admin admin : admins) {
                if (!isBCrypt(admin.getPassword())) {
                    System.out.println("Migrating password for Admin: " + admin.getUsername());
                    admin.setPassword(passwordEncoder.encode(admin.getPassword()));
                    adminRepo.save(admin);
                }
            }

            // 2. Migrate Students
            List<Student> students = studentRepo.findAll();
            for (Student student : students) {
                if (!isBCrypt(student.getPassword())) {
                    System.out.println("Migrating password for Student: " + student.getEmail());
                    student.setPassword(passwordEncoder.encode(student.getPassword()));
                    studentRepo.save(student);
                }
            }

            // 3. Migrate Instructors
            List<Instructor> instructors = instructorRepo.findAll();
            for (Instructor instructor : instructors) {
                if (!isBCrypt(instructor.getPassword())) {
                    System.out.println("Migrating password for Instructor: " + instructor.getEmail());
                    instructor.setPassword(passwordEncoder.encode(instructor.getPassword()));
                    instructorRepo.save(instructor);
                }
            }

            System.out.println("Password Migration Check Completed.");
        };
    }

    private boolean isBCrypt(String password) {
        // BCrypt hashes usually start with $2a$, $2b$ or $2y$ and are 60 chars long
        return password != null && (password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$")) && password.length() == 60;
    }
}
