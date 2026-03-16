package com.pathfinder.pathfinder_backend.service;

import com.pathfinder.pathfinder_backend.dto.CareerRecommendationDto;
import com.pathfinder.pathfinder_backend.model.Assessment;
import com.pathfinder.pathfinder_backend.model.Career;
import com.pathfinder.pathfinder_backend.model.User;
import com.pathfinder.pathfinder_backend.repository.CareerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final CareerRepository careerRepository;

    public List<CareerRecommendationDto> getRecommendations(Assessment assessment, User user) {
        List<Career> allCareers = careerRepository.findAll();

        return allCareers.stream()
                .map(career -> {
                    int matchScore = calculateMatchScore(career, assessment, user);
                    CareerRecommendationDto dto = new CareerRecommendationDto();
                    dto.setId(career.getId());
                    dto.setTitle(career.getTitle());
                    dto.setDescription(career.getDescription());
                    dto.setEducationPath(career.getEducationPath());
                    dto.setSalaryRange(career.getSalaryRange());
                    dto.setMatchPercentage(matchScore);
                    return dto;
                })
                .filter(dto -> dto.getMatchPercentage() > 40)
                .sorted((a, b) -> b.getMatchPercentage() - a.getMatchPercentage())
                .limit(3)
                .collect(Collectors.toList());
    }

    private int calculateMatchScore(Career career, Assessment assessment, User user) {
        int score = 0;
        int maxPossible = 0;

        // Aptitude: 0-25 points per category (only if career requires it)
        if (career.getMinLogicalScore() != null) {
            maxPossible += 25;
            if (assessment.getLogicalScore() >= career.getMinLogicalScore()) {
                score += 25;
            } else {
                // Partial credit: how close they got (0-25)
                int gap = career.getMinLogicalScore() - assessment.getLogicalScore();
                score += Math.max(0, 25 - gap * 2);
            }
        }
        if (career.getMinVerbalScore() != null) {
            maxPossible += 25;
            if (assessment.getVerbalScore() >= career.getMinVerbalScore()) {
                score += 25;
            } else {
                int gap = career.getMinVerbalScore() - assessment.getVerbalScore();
                score += Math.max(0, 25 - gap * 2);
            }
        }
        if (career.getMinNumericalScore() != null) {
            maxPossible += 25;
            if (assessment.getNumericalScore() >= career.getMinNumericalScore()) {
                score += 25;
            } else {
                int gap = career.getMinNumericalScore() - assessment.getNumericalScore();
                score += Math.max(0, 25 - gap * 2);
            }
        }
        if (career.getMinCreativeScore() != null) {
            maxPossible += 25;
            if (assessment.getCreativeScore() >= career.getMinCreativeScore()) {
                score += 25;
            } else {
                int gap = career.getMinCreativeScore() - assessment.getCreativeScore();
                score += Math.max(0, 25 - gap * 2);
            }
        }

        // Interests: up to 25 points
        if (user.getInterests() != null && career.getRelatedInterests() != null && !career.getRelatedInterests().isEmpty()) {
            maxPossible += 25;
            long matchingInterests = career.getRelatedInterests().stream()
                    .filter(interest -> user.getInterests().contains(interest))
                    .count();
            score += (int) (25 * matchingInterests / career.getRelatedInterests().size());
        }

        // Strong boost for technical careers when user prefers technical (fixes e.g. Interior Designer/Chef when user wants IT)
        boolean userPrefersTechnical = "technical".equals(assessment.getPreferredArea());
        boolean isTechnicalCareer = isTechnicalCareer(career);
        if (userPrefersTechnical && isTechnicalCareer) {
            score += 35;
            maxPossible += 35;
        }

        // Smaller boost when assessment profile is logical+numerical heavy (even without stated preference)
        int logical = assessment.getLogicalScore() != null ? assessment.getLogicalScore() : 0;
        int numerical = assessment.getNumericalScore() != null ? assessment.getNumericalScore() : 0;
        int verbal = assessment.getVerbalScore() != null ? assessment.getVerbalScore() : 0;
        int creative = assessment.getCreativeScore() != null ? assessment.getCreativeScore() : 0;
        boolean technicalAptitude = (logical + numerical) >= (verbal + creative);
        if (technicalAptitude && isTechnicalCareer && !userPrefersTechnical) {
            score += 15;
            maxPossible += 15;
        }

        // Scale to 0-100 so recommendations vary by user
        return maxPossible > 0 ? Math.min(100, (score * 100) / maxPossible) : 0;
    }

    /** Technical = IT/Programming/Data/Engineering roles only; exclude e.g. Firefighter, Police. */
    private boolean isTechnicalCareer(Career career) {
        // Must have strong logical/numerical requirement (>= 70) like real tech roles, not just any score
        boolean strongLogical = career.getMinLogicalScore() != null && career.getMinLogicalScore() >= 70;
        boolean strongNumerical = career.getMinNumericalScore() != null && career.getMinNumericalScore() >= 70;
        boolean notCreativeFocused = career.getMinCreativeScore() == null || career.getMinCreativeScore() <= 70;
        boolean aptitudeBased = (strongLogical || strongNumerical) && notCreativeFocused;

        // Or career has tech-related interests (Technology, Coding, Data, etc.)
        boolean hasTechInterests = career.getRelatedInterests() != null
                && career.getRelatedInterests().stream().anyMatch(this::isTechnicalInterest);

        return aptitudeBased || hasTechInterests;
    }

    private static final java.util.Set<String> TECHNICAL_INTERESTS = java.util.Set.of(
            "technology", "coding", "innovation", "data analysis", "mathematics", "problem solving",
            "programming", "data", "algorithms", "infrastructure", "security", "automation"
    );

    private boolean isTechnicalInterest(String interest) {
        return interest != null && TECHNICAL_INTERESTS.contains(interest.toLowerCase());
    }
}
