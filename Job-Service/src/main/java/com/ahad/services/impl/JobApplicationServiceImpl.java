package com.ahad.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import com.ahad.dto.application.JobApplicationRequestDTO;
import com.ahad.dto.application.JobApplicationResponseDTO;
import com.ahad.dto.application.JobApplicationUpdateDTO;
import com.ahad.enums.ApplicationStatus;
import com.ahad.exceptions.MappingFailedException;
import com.ahad.exceptions.ResourceNotFoundException;
import com.ahad.kafka.JobApplicationProducer;
import com.ahad.mapper.JobApplicationMapper;
import com.ahad.messages.ResponseMessage;
import com.ahad.models.JobApplication;
import com.ahad.models.JobPost;
import com.ahad.repos.JobApplicationRepository;
import com.ahad.repos.JobPostRepository;
import com.ahad.services.external.CompanyService;
import com.ahad.services.external.UserService;
import com.ahad.services.internal.JobApplicationService;

@Service
@Transactional
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobPostRepository jobPostRepository;
    private final UserService userService;
    private final CompanyService companyService;
    private final JobApplicationMapper jobApplicationMapper;
    private final JobApplicationProducer jobApplicationProducer;

    // ✅ Apply for a Job
    @Override
    public JobApplicationResponseDTO applyForJob(JobApplicationRequestDTO request) {
        JobPost jobPost = jobPostRepository.findById(request.getJobPostId())
                .orElseThrow(() -> new ResourceNotFoundException("JobPost " +
                        ResponseMessage.ID_NOT_FOUND + request.getJobPostId()));

        JobApplication jobApplication = Optional.ofNullable(jobApplicationMapper.toEntity(request))
                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));

        jobApplication.setJobPost(jobPost);
        jobApplication.setAppliedAt(LocalDateTime.now());
        jobApplication.setStatus(ApplicationStatus.APPLIED);

        JobApplication saved = jobApplicationRepository.save(jobApplication);

        jobApplicationProducer.publishJobApplicationEvent(jobApplicationMapper.toEvent(saved));

        return Optional.ofNullable(jobApplicationMapper.toResponseDTO(saved))
                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));
    }

    // ✅ Update Application
    @Override
    public JobApplicationResponseDTO updateJobApplication(UUID applicationId, JobApplicationUpdateDTO request) {
        JobApplication existing = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("JobApplication " +
                        ResponseMessage.ID_NOT_FOUND + applicationId));

        jobApplicationMapper.toEntity(request, existing);
        existing.setUpdatedAt(LocalDateTime.now());

        JobApplication saved = jobApplicationRepository.save(existing);

        return Optional.ofNullable(jobApplicationMapper.toResponseDTO(saved))
                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));
    }

    // ✅ Delete Application
    @Override
    public void deleteJobApplication(UUID applicationId) {
        JobApplication jobApplication = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("JobApplication " +
                        ResponseMessage.ID_NOT_FOUND + applicationId));

        jobApplicationRepository.delete(jobApplication);
    }

    // ✅ Get Applications by User
    @Override
    public List<JobApplicationResponseDTO> getJobApplicationsByUserId(UUID userId) {
        List<JobApplication> jobApplications = jobApplicationRepository.findByApplicantId(userId);
        return jobApplications.stream().map(jobApplicationMapper::toResponseDTO).toList();
    }

    // ✅ Get Applications by Job
    @Override
    public List<JobApplicationResponseDTO> getJobApplicationsByJobId(UUID jobId) {
        List<JobApplication> jobApplications = jobApplicationRepository.findByJobPostId(jobId);
        return jobApplications.stream().map(jobApplicationMapper::toResponseDTO).toList();
    }
}
