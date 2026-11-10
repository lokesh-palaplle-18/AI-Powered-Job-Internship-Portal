package com.jobportal.controller;

import com.jobportal.dto.request.JobPostRequest;
import com.jobportal.dto.response.JobPostResponse;
import com.jobportal.service.JobPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobPostController {
    private final JobPostService jobPostService;

    // Create a job post
    @PostMapping("/company/{companyId}")
    public ResponseEntity<JobPostResponse> createJobPost(
            @PathVariable Long companyId,
            @Valid @RequestBody JobPostRequest request
    ) {

        JobPostResponse response =
                jobPostService.createJobPost(request, companyId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Get job by ID
    @GetMapping("/{jobId}")
    public ResponseEntity<JobPostResponse> getJobPostById(
            @PathVariable Long jobId
    ) {

        JobPostResponse response =
                jobPostService.getJobPostById(jobId);

        return ResponseEntity.ok(response);
    }

    // Get all jobs with pagination
    @GetMapping
    public ResponseEntity<Page<JobPostResponse>> getAllJobPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        Page<JobPostResponse> response =
                jobPostService.getAllJobPosts(pageable);

        return ResponseEntity.ok(response);
    }
}
