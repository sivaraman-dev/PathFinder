package com.pathfinder.pathfinder_backend.dto;

import com.pathfinder.pathfinder_backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {
    private String token;
    private Long userId;
    private String email;
    private String name;
}
