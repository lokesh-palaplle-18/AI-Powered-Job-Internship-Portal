package com.jobportal.dto.response;

import com.jobportal.enums.JobType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class JobPostResponse {
    private Long id;

    private String title;

    private String description;

    private String location;

    private BigDecimal salary;

    private String requiredSkills;

    private JobType type;

    private LocalDate deadline;

    private String companyName;

    private LocalDateTime createdAt;
}
