package com.pathfinder.pathfinder_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "careers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "education_path", columnDefinition = "TEXT")
    private String educationPath;

    @Column(name = "salary_range")
    private String salaryRange;

    @Column(name = "growth_prospects")
    private String growthProspects;

    @Column(name = "min_logical_score")
    private Integer minLogicalScore;

    @Column(name = "min_verbal_score")
    private Integer minVerbalScore;

    @Column(name = "min_numerical_score")
    private Integer minNumericalScore;

    @Column(name = "min_creative_score")
    private Integer minCreativeScore;

    @ElementCollection
    @CollectionTable(name = "career_skills", joinColumns = @JoinColumn(name = "career_id"))
    @Column(name = "skill")
    private List<String> requiredSkills = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "career_interests", joinColumns = @JoinColumn(name = "career_id"))
    @Column(name = "interest")
    private List<String> relatedInterests = new ArrayList<>();
}
