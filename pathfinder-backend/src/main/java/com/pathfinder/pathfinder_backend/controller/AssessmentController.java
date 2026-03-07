package com.pathfinder.pathfinder_backend.controller;

import com.pathfinder.pathfinder_backend.dto.AssessmentDto;
import com.pathfinder.pathfinder_backend.dto.CareerRecommendationDto;
import com.pathfinder.pathfinder_backend.model.Assessment;
import com.pathfinder.pathfinder_backend.model.User;
import com.pathfinder.pathfinder_backend.service.AssessmentService;
import com.pathfinder.pathfinder_backend.service.RecommendationService;
import com.pathfinder.pathfinder_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assessment")
@RequiredArgsConstructor
public class AssessmentController {
    private final AssessmentService assessmentService;
    private final RecommendationService recommendationService;
    private final UserService userService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitAssessment(@RequestBody AssessmentDto dto,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername());
        Assessment assessment = assessmentService.submitAssessment(user.getId(), dto.getAnswers());
        List<CareerRecommendationDto> recommendations =
                recommendationService.getRecommendations(assessment, user);

        Map<String, Object> response = new HashMap<>();
        response.put("assessment", assessment);
        response.put("recommendations", recommendations);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLatestAssessment(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User user = userService.findByEmail(userDetails.getUsername());
            Assessment assessment = assessmentService.getLatestAssessment(user.getId());

            if (assessment != null) {
                List<CareerRecommendationDto> recommendations =
                        recommendationService.getRecommendations(assessment, user);

                Map<String, Object> response = new HashMap<>();
                response.put("assessment", assessment);
                response.put("recommendations", recommendations);

                return ResponseEntity.ok(response);
            }

            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
