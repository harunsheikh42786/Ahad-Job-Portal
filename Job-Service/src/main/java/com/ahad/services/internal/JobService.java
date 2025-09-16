package com.ahad.services.internal;

import java.util.List;
import java.util.UUID;

import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.data.domain.Page;

import com.ahad.dto.exports.JobPostForCompanyDTO;
import com.ahad.dto.exports.JobPostForUserDTO;
import com.ahad.dto.posts.JobPostProfileDTO;
import com.ahad.dto.posts.JobPostRequestDTO;
import com.ahad.dto.posts.JobPostResponseDTO;
import com.ahad.dto.posts.JobPostSearchDTO;
import com.ahad.dto.posts.JobPostUpdateDTO;

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

        // Optionally: Get all jobs without filters
        List<JobPostForCompanyDTO> getAllJobsByCompanyId(UUID companyId);

        // Optionally: Get all jobs without filters
        List<JobPostForUserDTO> getAllJobsByApplicantId(UUID applicantId);
}
