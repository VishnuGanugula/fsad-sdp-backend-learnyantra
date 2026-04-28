package com.klef.fsad.sdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.klef.fsad.sdp.entity.Submission;
import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByAssignmentId(long assignmentId);
    List<Submission> findByStudentId(int studentId);
    Submission findByAssignmentIdAndStudentId(long assignmentId, int studentId);
}
