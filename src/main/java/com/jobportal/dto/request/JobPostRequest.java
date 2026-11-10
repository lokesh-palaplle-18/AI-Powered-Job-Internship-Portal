package com.jobportal.dto.request;

import com.jobportal.enums.JobType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class JobPostRequest {
    @NotBlank(message = "Job title is required")
    private String title;

    @NotBlank(message = "Job description is required")
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    private String location;

    private BigDecimal salary;

    private String requiredSkills;

    @NotNull(message = "Job type is required")
    private JobType type;

    private LocalDate deadline;

}
