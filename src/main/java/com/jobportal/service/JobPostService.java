package com.jobportal.service;

import com.jobportal.dto.request.JobPostRequest;
import com.jobportal.dto.response.JobPostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobPostService {

    JobPostResponse createJobPost(
            JobPostRequest request,
            Long companyId
    );

    JobPostResponse getJobPostById(Long jobId);

    Page<JobPostResponse> getAllJobPosts(Pageable pageable);
}