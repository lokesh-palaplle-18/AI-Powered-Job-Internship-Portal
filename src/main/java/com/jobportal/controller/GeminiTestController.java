package com.jobportal.controller;


import com.jobportal.ai.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class GeminiTestController {

    private final GeminiService geminiService;

    @PostMapping("/test")
    public ResponseEntity<Map<String, String>> testGemini(
            @RequestBody Map<String, String> request) {

        String prompt = request.get("prompt");

        String aiResponse = geminiService.generateContent(prompt);

        return ResponseEntity.ok(
                Map.of("response", aiResponse)
        );
    }
}
