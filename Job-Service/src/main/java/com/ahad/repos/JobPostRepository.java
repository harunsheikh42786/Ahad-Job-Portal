package com.ahad.repos;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ahad.models.JobPost;

public interface JobPostRepository extends JpaRepository<JobPost, UUID> {

    boolean existsByTitleAndLocation(String title, String location);

    Page<JobPost> findByTitleContainingIgnoreCase(String jobTitle, Pageable pageable);

}
