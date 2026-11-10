package com.jobportal.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class ApplyJobRequest {
    @Size(
            max = 2000,
            message = "Cover letter cannot exceed 2000 characters"
    )
    private String coverLetter;
}
