package com.pathfinder.pathfinder_backend.repository;

import com.pathfinder.pathfinder_backend.model.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    List<Assessment> findByUserIdOrderByCompletedAtDesc(Long userId);
    Optional<Assessment> findFirstByUserIdOrderByCompletedAtDesc(Long userId);
}
