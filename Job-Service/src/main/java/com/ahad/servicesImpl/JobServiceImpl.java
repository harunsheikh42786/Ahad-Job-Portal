// JobServiceImpl (updated with matching exceptions)
package com.ahad.servicesImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ahad.dto.JobPostProfileDTO;
import com.ahad.dto.JobPostRequestDTO;
import com.ahad.dto.JobPostResponseDTO;
import com.ahad.dto.JobPostSearchDTO;
import com.ahad.dto.JobPostUpdateDTO;
import com.ahad.enums.SortBy;
import com.ahad.exceptions.DuplicateResourceException;
import com.ahad.exceptions.MappingFailedException;
import com.ahad.exceptions.ResourceNotFoundException;
import com.ahad.mapper.JobMapper;
import com.ahad.messages.ResponseMessage;
import com.ahad.models.JobPost;
import com.ahad.repos.JobPostRepository;
import com.ahad.services.JobService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

        private final JobPostRepository jobPostRepository;
        private final JobMapper jobMapper;

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
                return Optional.ofNullable(jobMapper.mapToJobPostResponseDTO(savedJobPost))
                                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));
        }

        // Get Job By Id
        @Override
        public JobPostProfileDTO getJobById(String id) {
                JobPost fetchedJob = jobPostRepository.findById(UUID.fromString(id))
                                .orElseThrow(() -> new ResourceNotFoundException("JobPost " +
                                                ResponseMessage.ID_NOT_FOUND + id));

                return Optional.ofNullable(jobMapper.mapToProfileDTO(fetchedJob))
                                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));
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
}
