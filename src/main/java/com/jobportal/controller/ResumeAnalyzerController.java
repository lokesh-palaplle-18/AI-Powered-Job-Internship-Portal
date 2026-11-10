package com.jobportal.controller;


import com.jobportal.ai.ResumeAnalyzerService;
import com.jobportal.ai.ResumeTextExtractorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/ai/resume")
@RequiredArgsConstructor
public class ResumeAnalyzerController {

    private final ResumeTextExtractorService textExtractorService;
    private final ResumeAnalyzerService resumeAnalyzerService;

    @PostMapping(
            value = "/analyze",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Map<String, String>> analyzeResume(
            @RequestParam("resume") MultipartFile resume) {

        // Step 1: Extract text from uploaded PDF
        String resumeText =
                textExtractorService.extractText(resume);

        // Step 2: Analyze extracted text using Gemini
        String analysis =
                resumeAnalyzerService.analyzeResume(resumeText);

        // Step 3: Return AI analysis
        return ResponseEntity.ok(
                Map.of("analysis", analysis)
        );
    }
}
