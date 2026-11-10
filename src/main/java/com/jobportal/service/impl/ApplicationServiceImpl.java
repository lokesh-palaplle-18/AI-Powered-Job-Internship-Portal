package com.jobportal.service.impl;

import com.jobportal.dto.response.ApplicationResponse;
import com.jobportal.entity.Application;
import com.jobportal.entity.JobPost;
import com.jobportal.entity.User;
import com.jobportal.enums.ApplicationStatus;
import com.jobportal.enums.Role;
import com.jobportal.exception.DuplicateResourceException;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.repository.ApplicationRepository;
import com.jobportal.repository.JobPostRepository;
import com.jobportal.repository.UserRepository;
import com.jobportal.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobPostRepository jobPostRepository;

    @Override
    @Transactional
    public ApplicationResponse applyForJob(
            Long studentId,
            Long jobId,
            String coverLetter
    ) {

        // Find student
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with ID: " + studentId
                ));

        // Verify student role
        if (student.getRole() != Role.STUDENT) {
            throw new IllegalArgumentException(
                    "Only students can apply for jobs"
            );
        }

        // Find job
        JobPost jobPost = jobPostRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Job post not found with ID: " + jobId
                ));

        // Check duplicate application
        boolean alreadyApplied =
                applicationRepository
                        .existsByStudent_IdAndJobPost_Id(
                                studentId,
                                jobId
                        );

        if (alreadyApplied) {
            throw new DuplicateResourceException(
                    "You have already applied for this job"
            );
        }

        // Create application
        Application application = new Application();

        application.setStudent(student);
        application.setJobPost(jobPost);
        application.setCoverLetter(coverLetter);
        application.setStatus(ApplicationStatus.PENDING);

        // Save application
        Application savedApplication =
                applicationRepository.save(application);

        return toResponse(savedApplication);
    }

    // Convert Application Entity into Response DTO
    private ApplicationResponse toResponse(
            Application application
    ) {

        ApplicationResponse response = new ApplicationResponse();

        response.setId(application.getId());

        response.setStudentId(
                application.getStudent().getId()
        );

        response.setStudentName(
                application.getStudent().getName()
        );

        response.setJobId(
                application.getJobPost().getId()
        );

        response.setJobTitle(
                application.getJobPost().getTitle()
        );

        response.setCoverLetter(
                application.getCoverLetter()
        );

        response.setStatus(
                application.getStatus()
        );

        response.setAppliedAt(
                application.getAppliedAt()
        );

        return response;
    }
}
