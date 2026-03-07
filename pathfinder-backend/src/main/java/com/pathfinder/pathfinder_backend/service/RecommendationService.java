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
                .limit(5)
                .collect(Collectors.toList());
    }

    private int calculateMatchScore(Career career, Assessment assessment, User user) {
        int score = 0;
        int factors = 0;

        // Check aptitude scores
        if (career.getMinLogicalScore() != null && assessment.getLogicalScore() >= career.getMinLogicalScore()) {
            score += 25;
            factors++;
        }
        if (career.getMinVerbalScore() != null && assessment.getVerbalScore() >= career.getMinVerbalScore()) {
            score += 25;
            factors++;
        }
        if (career.getMinNumericalScore() != null && assessment.getNumericalScore() >= career.getMinNumericalScore()) {
            score += 25;
            factors++;
        }
        if (career.getMinCreativeScore() != null && assessment.getCreativeScore() >= career.getMinCreativeScore()) {
            score += 25;
            factors++;
        }

        // Check interest match
        if (user.getInterests() != null && career.getRelatedInterests() != null) {
            long matchingInterests = career.getRelatedInterests().stream()
                    .filter(interest -> user.getInterests().contains(interest))
                    .count();

            if (matchingInterests > 0 && career.getRelatedInterests().size() > 0) {
                score += (int) (25 * matchingInterests / career.getRelatedInterests().size());
                factors++;
            }
        }

        return factors > 0 ? score / factors : 0;
    }
}
