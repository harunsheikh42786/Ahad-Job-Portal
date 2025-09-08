package com.ahad.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.ahad.dto.JobPostProfileDTO;
import com.ahad.dto.JobPostSearchDTO;
import com.ahad.dto.JobPostRequestDTO;
import com.ahad.dto.JobPostResponseDTO;
import com.ahad.dto.JobPostUpdateDTO;

public interface JobService {

        // Create a new job
        JobPostResponseDTO createJob(JobPostRequestDTO jobPostRequestDTO);

        // Get single job by ID
        JobPostProfileDTO getJobById(String id);

        // Update existing job
        JobPostResponseDTO updateJob(UUID id, JobPostUpdateDTO jobPostUpdateDTO);

        // Delete job by ID
        void deleteJob(UUID id);

        // Get all jobs by name with pagination + sorting
        Page<JobPostSearchDTO> getAllJobsByTitle(String title, int pageNumber,
                        int pageSize, String sortBy, String sortDir);

        // Optionally: Get all jobs without filters
        List<JobPostResponseDTO> getAllJobs();
}
