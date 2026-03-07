package com.pathfinder.pathfinder_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "assessments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "logical_score")
    private Integer logicalScore;

    @Column(name = "verbal_score")
    private Integer verbalScore;

    @Column(name = "numerical_score")
    private Integer numericalScore;

    @Column(name = "creative_score")
    private Integer creativeScore;

    @Column(name = "completed_at")
    private LocalDateTime completedAt = LocalDateTime.now();
}
