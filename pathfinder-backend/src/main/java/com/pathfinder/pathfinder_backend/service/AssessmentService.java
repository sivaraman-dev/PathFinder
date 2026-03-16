package com.pathfinder.pathfinder_backend.service;

import com.pathfinder.pathfinder_backend.dto.AssessmentDto;
import com.pathfinder.pathfinder_backend.model.Assessment;
import com.pathfinder.pathfinder_backend.model.User;
import com.pathfinder.pathfinder_backend.repository.AssessmentRepository;
import com.pathfinder.pathfinder_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AssessmentService {
    private final AssessmentRepository assessmentRepository;
    private final UserRepository userRepository;

    public Assessment submitAssessment(Long userId, Map<String, Integer> answers, String preferredArea) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Calculate scores based on answers
        int logicalScore = calculateScore(answers, "logical");
        int verbalScore = calculateScore(answers, "verbal");
        int numericalScore = calculateScore(answers, "numerical");
        int creativeScore = calculateScore(answers, "creative");

        Assessment assessment = new Assessment();
        assessment.setUser(user);
        assessment.setLogicalScore(logicalScore);
        assessment.setVerbalScore(verbalScore);
        assessment.setNumericalScore(numericalScore);
        assessment.setCreativeScore(creativeScore);
        if (preferredArea != null && !preferredArea.isBlank()) {
            assessment.setPreferredArea(preferredArea.trim().toLowerCase());
        }

        return assessmentRepository.save(assessment);
    }

    private int calculateScore(Map<String, Integer> answers, String category) {
        // Option indices 0-3 (4 options); normalize to 0-100 so scores match career min thresholds
        int score = 0;
        int count = 0;

        for (Map.Entry<String, Integer> entry : answers.entrySet()) {
            if (entry.getKey().contains(category)) {
                score += entry.getValue();
                count++;
            }
        }

        // Max sum = count * 3 (best option index 3), so scale to 0-100
        return count > 0 ? (score * 100) / (count * 3) : 0;
    }

    public Assessment getLatestAssessment(Long userId) {
        return assessmentRepository.findFirstByUserIdOrderByCompletedAtDesc(userId)
                .orElse(null);
    }
}
