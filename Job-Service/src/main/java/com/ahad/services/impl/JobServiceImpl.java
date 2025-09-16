// JobServiceImpl (updated with matching exceptions)
package com.ahad.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ahad.dto.exports.JobApplicationForCompanyDTO;
import com.ahad.dto.exports.JobPostForCompanyDTO;
import com.ahad.dto.exports.JobPostForUserDTO;
import com.ahad.dto.imports.CompanyForJobDTO;
import com.ahad.dto.imports.UserForJobDTO;
import com.ahad.dto.posts.JobPostProfileDTO;
import com.ahad.dto.posts.JobPostRequestDTO;
import com.ahad.dto.posts.JobPostResponseDTO;
import com.ahad.dto.posts.JobPostSearchDTO;
import com.ahad.dto.posts.JobPostUpdateDTO;
import com.ahad.enums.SortBy;
import com.ahad.exceptions.DuplicateResourceException;
import com.ahad.exceptions.MappingFailedException;
import com.ahad.exceptions.ResourceNotFoundException;
import com.ahad.helper.ApiResponse;
import com.ahad.kafka.JobPostedProducer;
import com.ahad.mapper.JobApplicationMapper;
import com.ahad.mapper.JobMapper;
import com.ahad.messages.ResponseMessage;
import com.ahad.models.JobPost;
import com.ahad.repos.JobPostRepository;
import com.ahad.services.external.CompanyService;
import com.ahad.services.external.UserService;
import com.ahad.services.internal.JobService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

        private final JobPostRepository jobPostRepository;
        private final JobMapper jobMapper;
        private final JobApplicationMapper jobApplicationMapper;
        private final CompanyService companyService;
        private final UserService userService;
        private final JobPostedProducer jobPostedProducer;

        // Register new Job
        @Override
        public JobPostResponseDTO createJob(JobPostRequestDTO jobPostRequestDTO) {

                // ✅ Example duplicate check: job title + location (optional)
                if (jobPostRepository.existsByTitleAndLocation(
                                jobPostRequestDTO.getTitle(),
                                jobPostRequestDTO.getLocation())) {
                        throw new DuplicateResourceException(ResponseMessage.DUPLICATE_RESOURCE);
                }

                // ✅ RequestDTO → Entity
                JobPost jobPost = Optional.ofNullable(jobMapper.mapToJobPost(jobPostRequestDTO))
                                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));

                // ✅ Set default values
                jobPost.setPostedAt(LocalDateTime.now());
                jobPost.setActive(true);

                // ✅ Save to DB
                JobPost savedJobPost = jobPostRepository.save(jobPost);

                // ✅ Entity → ResponseDTO
                JobPostResponseDTO responseDTO = Optional.ofNullable(jobMapper.mapToJobPostResponseDTO(savedJobPost))
                                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));
                // Send Kafka event
                jobPostedProducer.sendJobPostedEvent(jobMapper.mapToJobEventForKafkaDTO(savedJobPost));
                // Return response
                return responseDTO;
        }

        // Get Job By Id
        @Override
        public JobPostProfileDTO getJobById(String id) {
                JobPost fetchedJob = jobPostRepository.findById(UUID.fromString(id))
                                .orElseThrow(() -> new ResourceNotFoundException("JobPost " +
                                                ResponseMessage.ID_NOT_FOUND + id));

                JobPostProfileDTO profileDTO = Optional.ofNullable(jobMapper.mapToProfileDTO(fetchedJob))
                                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));

                ApiResponse<UserForJobDTO> userResponse = userService
                                .getUserByIdForJob(fetchedJob.getRecruiterId());
                if (userResponse.isSuccess()) {
                        profileDTO.setRecruiter(userResponse.getData());
                }
                ApiResponse<CompanyForJobDTO> companyResponse = companyService
                                .getCompanyByIdForJob(fetchedJob.getCompanyId());
                if (companyResponse.isSuccess()) {
                        profileDTO.setCompany(companyResponse.getData());
                }
                return profileDTO;
        }

        @Override
        public JobPostResponseDTO updateJob(UUID jobId, JobPostUpdateDTO dto) {
                JobPost existingJob = jobPostRepository.findById(jobId)
                                .orElseThrow(() -> new ResourceNotFoundException("JobPost " +
                                                ResponseMessage.ID_NOT_FOUND + jobId.toString()));
                jobMapper.toEntity(dto, existingJob);

                JobPost savedJob = jobPostRepository.save(existingJob);

                return Optional.ofNullable(jobMapper.mapToJobPostResponseDTO(savedJob))
                                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));
        }

        // DeleteJob By Id
        @Override
        public void deleteJob(UUID id) {
                JobPost job = jobPostRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("JobPost " +
                                                ResponseMessage.ID_NOT_FOUND + id.toString()));
                jobPostRepository.delete(job);
        }

        @Override
        public List<JobPostResponseDTO> getAllJobs() {
                List<JobPost> listOfJobs = this.jobPostRepository.findAll();
                return jobMapper.mapToResponseDTOs(listOfJobs);
        }

        @Override
        public Page<JobPostSearchDTO> getAllJobsByTitle(String jobTitle, int pageNumber, int pageSize, String sortBy,
                        String sortDir) {
                Sort sort = sortDir.equalsIgnoreCase(SortBy.ASCENDING.toString())
                                ? Sort.by(sortBy).ascending()
                                : Sort.by(sortBy).descending();

                Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

                Page<JobPost> jobs = jobPostRepository.findByTitleContainingIgnoreCase(jobTitle, pageable);

                return jobs.map(jobMapper::mapToSearchDTO);
        }

        @Override
        public List<JobPostForCompanyDTO> getAllJobsByCompanyId(UUID companyId) {
                return jobPostRepository.findByCompanyId(companyId)
                                .stream()
                                .map(job -> {
                                        JobPostForCompanyDTO dto = jobMapper.mapToJobPostForCompanyDTO(job);

                                        dto.setApplications(
                                                        job.getApplications().stream()
                                                                        .map(application -> {
                                                                                JobApplicationForCompanyDTO appDTO = jobApplicationMapper
                                                                                                .toJobApplicationForCompanyDTO(
                                                                                                                application);

                                                                                // ✅ Fetch user details
                                                                                ApiResponse<UserForJobDTO> userForJob = userService
                                                                                                .getUserByIdForJob(
                                                                                                                application.getApplicantId());

                                                                                if (userForJob.isSuccess() && userForJob
                                                                                                .getData() != null) {
                                                                                        appDTO.setApplicant(userForJob
                                                                                                        .getData());
                                                                                }
                                                                                return appDTO;
                                                                        })
                                                                        .toList());

                                        return dto;
                                })
                                .toList();
        }

        @Override
        public List<JobPostForUserDTO> getAllJobsByApplicantId(UUID applicantId) {
                List<JobPost> jobs = this.jobPostRepository.findByApplications_ApplicantId(applicantId);
                return jobs.stream().map(job -> {
                        JobPostForUserDTO jobDTO = jobMapper.mapToJobPostForUserDTO(job);
                        // Set the specific application for this applicant
                        jobDTO.setApplication(job.getApplications().stream()
                                        .filter(application -> application.getApplicantId().equals(applicantId))
                                        .map(application -> jobApplicationMapper
                                                        .toJobApplicationForUserDTO(application))
                                        .findFirst()
                                        .orElse(null));
                        // Fetch and set company details
                        ApiResponse<CompanyForJobDTO> companyResponse = companyService
                                        .getCompanyByIdForJob(job.getCompanyId());
                        if (companyResponse.isSuccess()) {
                                jobDTO.setCompany(companyResponse.getData());
                        }
                        return jobDTO;
                }).toList();
        }
}
