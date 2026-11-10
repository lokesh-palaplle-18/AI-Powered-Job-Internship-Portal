package com.jobportal.repository;

import com.jobportal.entity.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository
            extends JpaRepository<Application, Long> {

        boolean existsByStudent_IdAndJobPost_Id(
                Long studentId,
                Long jobPostId
        );

        Page<Application> findByStudent_Id(
                Long studentId,
                Pageable pageable
        );

        Page<Application> findByJobPost_Id(
                Long jobPostId,
                Pageable pageable
        );
}
