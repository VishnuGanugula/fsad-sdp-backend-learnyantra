package com.klef.fsad.sdp.repository;

import com.klef.fsad.sdp.entity.RecentActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RecentActivityRepository extends JpaRepository<RecentActivity, Long> {
    List<RecentActivity> findByStudentIdOrderByVisitedAtDesc(int studentId);
    Optional<RecentActivity> findByStudentIdAndItemTypeAndItemId(int studentId, String itemType, Long itemId);
}
