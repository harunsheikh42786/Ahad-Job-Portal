package com.ahad.controllers;

import com.ahad.dto.exports.JobPostForCompanyDTO;
import com.ahad.dto.exports.JobPostForUserDTO;
import com.ahad.dto.posts.JobPostProfileDTO;
import com.ahad.dto.posts.JobPostRequestDTO;
import com.ahad.dto.posts.JobPostResponseDTO;
import com.ahad.dto.posts.JobPostSearchDTO;
import com.ahad.dto.posts.JobPostUpdateDTO;
import com.ahad.helper.ApiResponse;
import com.ahad.helper.ApiVersion;
import com.ahad.messages.ResponseMessage;
import com.ahad.services.internal.JobService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(ApiVersion.V1 + "/jobs")
@RequiredArgsConstructor
public class JobController {

        private final JobService jobService;

        @PostMapping
        public ResponseEntity<ApiResponse<JobPostResponseDTO>> createJob(
                        @Valid @RequestBody JobPostRequestDTO request) {

                JobPostResponseDTO createdJob = jobService.createJob(request);

                ApiResponse<JobPostResponseDTO> response = ApiResponse.<JobPostResponseDTO>builder()
                                .success(true)
                                .message(ResponseMessage.CREATED)
                                .data(createdJob)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.CREATED.value())
                                .build();

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @GetMapping("/{jobId}")
        public ResponseEntity<ApiResponse<JobPostProfileDTO>> getJobById(@PathVariable String jobId) {
                JobPostProfileDTO job = jobService.getJobById(jobId);

                ApiResponse<JobPostProfileDTO> response = ApiResponse.<JobPostProfileDTO>builder()
                                .success(true)
                                .message(ResponseMessage.FETCHED)
                                .data(job)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();

                return ResponseEntity.ok(response);
        }

        @PutMapping("/{jobId}")
        public ResponseEntity<ApiResponse<JobPostResponseDTO>> updateJob(
                        @PathVariable UUID jobId,
                        @Valid @RequestBody JobPostUpdateDTO request) {

                JobPostResponseDTO updatedJob = jobService.updateJob(jobId, request);

                ApiResponse<JobPostResponseDTO> response = ApiResponse.<JobPostResponseDTO>builder()
                                .success(true)
                                .message(ResponseMessage.UPDATED)
                                .data(updatedJob)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();

                return ResponseEntity.ok(response);
        }

        @DeleteMapping("/{jobId}")
        public ResponseEntity<ApiResponse<Boolean>> deleteJob(@PathVariable UUID jobId) {
                jobService.deleteJob(jobId);

                ApiResponse<Boolean> response = ApiResponse.<Boolean>builder()
                                .success(true)
                                .data(true)
                                .message(ResponseMessage.DELETED)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();

                return ResponseEntity.ok(response);
        }

        @GetMapping
        public ResponseEntity<ApiResponse<List<JobPostResponseDTO>>> getAllJobs() {
                List<JobPostResponseDTO> jobs = jobService.getAllJobs();

                ApiResponse<List<JobPostResponseDTO>> response = ApiResponse.<List<JobPostResponseDTO>>builder()
                                .success(true)
                                .data(jobs)
                                .message(jobs.isEmpty() ? ResponseMessage.NO_DATA : ResponseMessage.FETCHED)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();

                return ResponseEntity.ok(response);
        }

        @GetMapping("/company/all/{companyId}")
        public ResponseEntity<ApiResponse<List<JobPostForCompanyDTO>>> getJobsByCompanyId(
                        @PathVariable UUID companyId) {
                List<JobPostForCompanyDTO> jobs = jobService.getAllJobsByCompanyId(companyId);

                ApiResponse<List<JobPostForCompanyDTO>> response = ApiResponse.<List<JobPostForCompanyDTO>>builder()
                                .success(true)
                                .data(jobs)
                                .message(jobs.isEmpty() ? ResponseMessage.NO_DATA : ResponseMessage.FETCHED)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();

                return ResponseEntity.ok(response);
        }

        @GetMapping("/user/all/{userId}")
        public ResponseEntity<ApiResponse<List<JobPostForUserDTO>>> getJobsByUserId(@PathVariable UUID userId) {
                List<JobPostForUserDTO> jobs = jobService.getAllJobsByApplicantId(userId);

                ApiResponse<List<JobPostForUserDTO>> response = ApiResponse.<List<JobPostForUserDTO>>builder()
                                .success(true)
                                .data(jobs)
                                .message(jobs.isEmpty() ? ResponseMessage.NO_DATA : ResponseMessage.FETCHED)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();

                return ResponseEntity.ok(response);
        }

        @GetMapping("/search")
        public ResponseEntity<ApiResponse<Page<JobPostSearchDTO>>> searchJobsByTitle(
                        @RequestParam String title,
                        @RequestParam(defaultValue = "0") int pageNumber,
                        @RequestParam(defaultValue = "10") int pageSize,
                        @RequestParam(defaultValue = "id") String sortBy,
                        @RequestParam(defaultValue = "ASC") String sortDir) {

                Page<JobPostSearchDTO> jobs = jobService.getAllJobsByTitle(title, pageNumber, pageSize, sortBy,
                                sortDir);

                ApiResponse<Page<JobPostSearchDTO>> response = ApiResponse.<Page<JobPostSearchDTO>>builder()
                                .success(true)
                                .data(jobs)
                                .message(jobs.isEmpty() ? ResponseMessage.NO_DATA : ResponseMessage.FETCHED)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();

                return ResponseEntity.ok(response);
        }
}
