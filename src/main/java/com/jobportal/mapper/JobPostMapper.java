package com.jobportal.mapper;


import com.jobportal.dto.request.JobPostRequest;
import com.jobportal.dto.response.JobPostResponse;
import com.jobportal.entity.JobPost;
import com.jobportal.entity.User;

public class JobPostMapper {


        private JobPostMapper() {
        }

        // Convert JobPostRequest DTO into JobPost Entity
        public static JobPost toEntity(
                JobPostRequest request,
                User postedBy
        ) {

            JobPost jobPost = new JobPost();

            jobPost.setTitle(request.getTitle());
            jobPost.setDescription(request.getDescription());
            jobPost.setLocation(request.getLocation());
            jobPost.setSalary(request.getSalary());
            jobPost.setRequiredSkills(request.getRequiredSkills());
            jobPost.setType(request.getType());
            jobPost.setDeadline(request.getDeadline());

            jobPost.setPostedBy(postedBy);

            return jobPost;
        }

        // Convert JobPost Entity into JobPostResponse DTO
        public static JobPostResponse toResponse(JobPost jobPost) {

            JobPostResponse response = new JobPostResponse();

            response.setId(jobPost.getId());
            response.setTitle(jobPost.getTitle());
            response.setDescription(jobPost.getDescription());
            response.setLocation(jobPost.getLocation());
            response.setSalary(jobPost.getSalary());
            response.setRequiredSkills(jobPost.getRequiredSkills());
            response.setType(jobPost.getType());
            response.setDeadline(jobPost.getDeadline());
            response.setCreatedAt(jobPost.getCreatedAt());

            if (jobPost.getPostedBy() != null) {
                String companyName = jobPost.getPostedBy().getCompanyName();

                if (companyName == null || companyName.isBlank()) {
                    companyName = jobPost.getPostedBy().getName();
                }

                response.setCompanyName(companyName);
            }

            return response;
        }
    }



