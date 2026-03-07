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

    public Assessment submitAssessment(Long userId, Map<String, Integer> answers) {
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

        return assessmentRepository.save(assessment);
    }

    private int calculateScore(Map<String, Integer> answers, String category) {
        // Simple scoring logic - you can make this more sophisticated
        int score = 0;
        int count = 0;

        for (Map.Entry<String, Integer> entry : answers.entrySet()) {
            if (entry.getKey().contains(category)) {
                score += entry.getValue();
                count++;
            }
        }

        return count > 0 ? (score * 100) / (count * 5) : 0;
    }

    public Assessment getLatestAssessment(Long userId) {
        return assessmentRepository.findFirstByUserIdOrderByCompletedAtDesc(userId)
                .orElse(null);
    }
}
