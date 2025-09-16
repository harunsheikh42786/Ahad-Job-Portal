package com.ahad.services.internal;

import java.util.List;
import java.util.UUID;

import com.ahad.dto.application.JobApplicationRequestDTO;
import com.ahad.dto.application.JobApplicationResponseDTO;
import com.ahad.dto.application.JobApplicationUpdateDTO;

public interface JobApplicationService {

    JobApplicationResponseDTO applyForJob(JobApplicationRequestDTO request);

    List<JobApplicationResponseDTO> getJobApplicationsByUserId(UUID userId);

    List<JobApplicationResponseDTO> getJobApplicationsByJobId(UUID jobId);

    JobApplicationResponseDTO updateJobApplication(UUID applicationId, JobApplicationUpdateDTO request);

    void deleteJobApplication(UUID applicationId);

}
