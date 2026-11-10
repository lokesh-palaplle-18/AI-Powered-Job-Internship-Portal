package com.jobportal.ai;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeAnalyzerService {

    private final GeminiService geminiService;

    public String analyzeResume(String resumeText) {

        if (resumeText == null || resumeText.isBlank()) {
            throw new IllegalArgumentException(
                    "Resume text cannot be empty"
            );
        }

        String prompt = """
                You are an expert technical recruiter
                and professional resume analyzer.

                Analyze the following resume carefully.

                Provide the analysis using these sections:

                1. Professional Summary
                2. Technical Skills
                3. Programming Languages
                4. Frameworks and Technologies
                5. Education
                6. Projects
                7. Work Experience
                8. Strengths
                9. Weaknesses
                10. Missing Skills
                11. Resume Improvement Suggestions
                12. Overall Resume Score out of 100

                Give practical and honest feedback.
                Do not invent qualifications or experience.
                If information is missing, mention that.

                Resume Content:
                """ + resumeText;

        return geminiService.generateContent(prompt);
    }
}
