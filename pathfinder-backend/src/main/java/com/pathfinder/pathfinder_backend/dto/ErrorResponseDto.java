package com.pathfinder.pathfinder_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {
    private String message;
    private String error;
    private int status;
    private LocalDateTime timestamp = LocalDateTime.now();
}
