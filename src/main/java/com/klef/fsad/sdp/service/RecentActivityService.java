package com.klef.fsad.sdp.service;

import com.klef.fsad.sdp.entity.RecentActivity;
import com.klef.fsad.sdp.repository.RecentActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecentActivityService {
    @Autowired
    private RecentActivityRepository repository;

    public void logActivity(int studentId, String type, Long itemId, String title) {
        Optional<RecentActivity> existing = repository.findByStudentIdAndItemTypeAndItemId(studentId, type, itemId);
        RecentActivity activity;
        if (existing.isPresent()) {
            activity = existing.get();
            activity.setVisitedAt(LocalDateTime.now());
        } else {
            activity = new RecentActivity();
            activity.setStudentId(studentId);
            activity.setItemType(type);
            activity.setItemId(itemId);
            activity.setTitle(title);
        }
        repository.save(activity);
    }

    public List<RecentActivity> getRecentActivities(int studentId) {
        return repository.findByStudentIdOrderByVisitedAtDesc(studentId)
                .stream()
                .limit(5)
                .collect(Collectors.toList());
    }
}
