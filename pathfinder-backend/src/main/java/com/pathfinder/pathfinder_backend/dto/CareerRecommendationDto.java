package com.pathfinder.pathfinder_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareerRecommendationDto {
    private Long id;
    private String title;
    private String description;
    private String educationPath;
    private String salaryRange;
    private Integer matchPercentage;
}
