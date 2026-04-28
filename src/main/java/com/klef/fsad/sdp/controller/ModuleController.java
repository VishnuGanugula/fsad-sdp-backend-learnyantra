package com.klef.fsad.sdp.controller;

import com.klef.fsad.sdp.entity.Module;
import com.klef.fsad.sdp.entity.Courses;
import com.klef.fsad.sdp.service.InstructorService;
import com.klef.fsad.sdp.service.StudentService;
import com.klef.fsad.sdp.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public ResponseEntity<String> addModule(
            @RequestParam("title") String title,
            @RequestParam("sequenceOrder") int sequenceOrder,
            @RequestParam("courseId") long courseId,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            Module module = new Module();
            module.setTitle(title);
            module.setSequenceOrder(sequenceOrder);
            
            Courses course = new Courses();
            course.setId(courseId);
            module.setCourse(course);

            if (file != null && !file.isEmpty()) {
                String fileName = fileStorageService.storeFile(file);
                module.setContentUrl("/uploads/" + fileName);
            } else {
                module.setContentUrl("No Content Provided");
            }

            String result = instructorService.addModule(module);
            return ResponseEntity.status(201).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding module: " + e.getMessage());
        }
    }

    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'INSTRUCTOR')")
    public ResponseEntity<List<Module>> getModulesByCourse(@PathVariable long courseId) {
        List<Module> modules = studentService.getModulesByCourse(courseId);
        return ResponseEntity.ok(modules);
    }
}
