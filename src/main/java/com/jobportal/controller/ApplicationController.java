package com.jobportal.controller;

import com.jobportal.dto.request.ApplyJobRequest;
import com.jobportal.dto.response.ApplicationResponse;
import com.jobportal.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    // Apply for a job
    @PostMapping("/student/{studentId}/job/{jobId}")
    public ResponseEntity<ApplicationResponse> applyForJob(

            @PathVariable Long studentId,

            @PathVariable Long jobId,

            @Valid @RequestBody ApplyJobRequest request
    ) {

        ApplicationResponse response =
                applicationService.applyForJob(
                        studentId,
                        jobId,
                        request.getCoverLetter()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
