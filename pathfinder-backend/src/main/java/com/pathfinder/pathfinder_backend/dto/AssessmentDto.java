package com.pathfinder.pathfinder_backend.dto;

import lombok.Data;
import java.util.Map;

@Data
public class AssessmentDto {
    private Map<String, Integer> answers;
    private Long userId;
    /** Career area interest: technical, creative, business, healthcare, other */
    private String preferredArea;
}
