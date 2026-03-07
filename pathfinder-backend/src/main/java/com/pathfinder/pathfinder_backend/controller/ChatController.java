package com.pathfinder.pathfinder_backend.controller;

import com.pathfinder.pathfinder_backend.model.User;
import com.pathfinder.pathfinder_backend.service.AIService;
import com.pathfinder.pathfinder_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final AIService aiService;
    private final UserService userService;

    @PostMapping("/message")
    public ResponseEntity<?> sendMessage(@RequestBody Map<String, String> request,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        try {
            String message = request.get("message");
            User user = userService.findByEmail(userDetails.getUsername());

            String context = String.format("Student: %s, Age: %d, Class: %d",
                    user.getName(), user.getAge(), user.getClassLevel());

            String response = aiService.getChatResponse(message, context);

            Map<String, String> result = new HashMap<>();
            result.put("response", response);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
