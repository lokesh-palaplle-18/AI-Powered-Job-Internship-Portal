package com.jobportal.dto.response;

import com.jobportal.enums.ApplicationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplicationResponse {

    private Long id;

    private Long studentId;

    private String studentName;

    private Long jobId;

    private String jobTitle;

    private String coverLetter;

    private ApplicationStatus status;

    private LocalDateTime appliedAt;
}
