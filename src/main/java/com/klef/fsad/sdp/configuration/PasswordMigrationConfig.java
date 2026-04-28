package com.klef.fsad.sdp.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

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
            PasswordEncoder passwordEncoder,
            PlatformTransactionManager transactionManager) {

        return args -> {
            TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);

            System.out.println("=== Starting Password Migration Check ===");

            // 1. Seed default admin if none exists
            txTemplate.execute(status -> {
                List<Admin> admins = adminRepo.findAll();
                if (admins.isEmpty()) {
                    System.out.println("[Migration] No admin found — seeding default admin.");
                    Admin defaultAdmin = new Admin();
                    defaultAdmin.setUsername("admin");
                    defaultAdmin.setPassword(passwordEncoder.encode("admin123"));
                    adminRepo.save(defaultAdmin);
                    System.out.println("[Migration] Default admin created with username='admin' and password='admin123'");
                }
                return null;
            });

            // 2. Migrate Admin plain-text passwords → BCrypt
            txTemplate.execute(status -> {
                List<Admin> admins = adminRepo.findAll();
                for (Admin admin : admins) {
                    if (!isBCrypt(admin.getPassword())) {
                        System.out.println("[Migration] Hashing plain-text password for Admin: " + admin.getUsername());
                        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
                        adminRepo.save(admin);
                        System.out.println("[Migration] Admin '" + admin.getUsername() + "' migrated successfully.");
                    }
                }
                return null;
            });

            // 3. Migrate Student plain-text passwords → BCrypt
            txTemplate.execute(status -> {
                List<Student> students = studentRepo.findAll();
                for (Student student : students) {
                    if (!isBCrypt(student.getPassword())) {
                        System.out.println("[Migration] Hashing plain-text password for Student: " + student.getEmail());
                        student.setPassword(passwordEncoder.encode(student.getPassword()));
                        studentRepo.save(student);
                    }
                }
                return null;
            });

            // 4. Migrate Instructor plain-text passwords → BCrypt
            txTemplate.execute(status -> {
                List<Instructor> instructors = instructorRepo.findAll();
                for (Instructor instructor : instructors) {
                    if (!isBCrypt(instructor.getPassword())) {
                        System.out.println("[Migration] Hashing plain-text password for Instructor: " + instructor.getEmail());
                        instructor.setPassword(passwordEncoder.encode(instructor.getPassword()));
                        instructorRepo.save(instructor);
                    }
                }
                return null;
            });

            System.out.println("=== Password Migration Check Completed ===");
        };
    }

    /**
     * BCrypt hashes start with $2a$, $2b$, or $2y$ and are exactly 60 characters long.
     */
    private boolean isBCrypt(String password) {
        return password != null
                && (password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$"))
                && password.length() == 60;
    }
}
