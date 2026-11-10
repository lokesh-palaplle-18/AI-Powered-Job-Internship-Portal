package com.jobportal.service.impl;

import com.jobportal.dto.request.JobPostRequest;
import com.jobportal.dto.response.JobPostResponse;
import com.jobportal.entity.JobPost;
import com.jobportal.entity.User;
import com.jobportal.enums.Role;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.mapper.JobPostMapper;
import com.jobportal.repository.JobPostRepository;
import com.jobportal.repository.UserRepository;
import com.jobportal.service.JobPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JobPostServiceImpl implements JobPostService {
    private final JobPostRepository jobPostRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public JobPostResponse createJobPost(
            JobPostRequest request,
            Long companyId
    ) {

        User company = userRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Company not found with ID: " + companyId
                ));

        if (company.getRole() != Role.COMPANY) {
            throw new IllegalArgumentException(
                    "Only companies can create job posts"
            );
        }

        JobPost jobPost = JobPostMapper.toEntity(
                request,
                company
        );

        JobPost savedJob = jobPostRepository.save(jobPost);

        return JobPostMapper.toResponse(savedJob);
    }

    @Override
    @Transactional(readOnly = true)
    public JobPostResponse getJobPostById(Long jobId) {

        JobPost jobPost = jobPostRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Job post not found with ID: " + jobId
                ));

        return JobPostMapper.toResponse(jobPost);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobPostResponse> getAllJobPosts(Pageable pageable) {

        return jobPostRepository.findAll(pageable)
                .map(JobPostMapper::toResponse);
    }



}
