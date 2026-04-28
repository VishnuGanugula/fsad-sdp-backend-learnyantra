package com.klef.fsad.sdp.controller;

import com.klef.fsad.sdp.entity.RecentActivity;
import com.klef.fsad.sdp.service.RecentActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/activity")
@CrossOrigin
public class RecentActivityController {
    @Autowired
    private RecentActivityService service;

    @PostMapping("/log")
    public void logActivity(@RequestParam int studentId, @RequestParam String type, @RequestParam Long itemId, @RequestParam String title) {
        service.logActivity(studentId, type, itemId, title);
    }

    @GetMapping("/recent/{studentId}")
    public List<RecentActivity> getRecentActivities(@PathVariable int studentId) {
        return service.getRecentActivities(studentId);
    }
}
