package com.jobportal.service;

import com.jobportal.dto.response.ApplicationResponse;

public interface ApplicationService {
    ApplicationResponse applyForJob(
            Long studentId,
            Long jobId,
            String coverLetter
    );
}
