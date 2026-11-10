package com.jobportal.repository;

import com.jobportal.entity.JobPost;
import com.jobportal.enums.JobType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostRepository extends JpaRepository<JobPost,Long> {
    Page<JobPost> findByType(JobType type, Pageable pageable);

    Page<JobPost> findByLocationContainingIgnoreCase(
            String location,
            Pageable pageable
    );
}
