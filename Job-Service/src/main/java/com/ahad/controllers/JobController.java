package com.ahad.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.ahad.dto.JobPostProfileDTO;
import com.ahad.dto.JobPostRequestDTO;
import com.ahad.dto.JobPostResponseDTO;
import com.ahad.dto.JobPostSearchDTO;
import com.ahad.dto.JobPostUpdateDTO;
import com.ahad.helper.ApiResponse;
import com.ahad.messages.ResponseMessage;
import com.ahad.services.JobService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/jobs") // plural is REST best practice
@RequiredArgsConstructor // Lombok constructor injection
public class JobController {

        private final JobService jobService;

        @PostMapping("/register-job")
        public ResponseEntity<ApiResponse<JobPostResponseDTO>> registerJob(
                        @Valid @RequestBody JobPostRequestDTO jobPostRequestDTO) {

                JobPostResponseDTO createdJob = jobService.createJob(jobPostRequestDTO);

                ApiResponse<JobPostResponseDTO> apiResponse = ApiResponse.<JobPostResponseDTO>builder()
                                .success(true)
                                .message(ResponseMessage.CREATED)
                                .data(createdJob)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.CREATED.value())
                                .errorCode(null)
                                .errorDetails(null)
                                .build();

                return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }

        @GetMapping("/get/{jobId}")
        public ResponseEntity<ApiResponse<JobPostProfileDTO>> getJobById(@PathVariable String jobId) {
                JobPostProfileDTO jobPostProfileDTO = jobService.getJobById(jobId);

                ApiResponse<JobPostProfileDTO> apiResponse = ApiResponse.<JobPostProfileDTO>builder()
                                .success(true)
                                .message(ResponseMessage.FETCHED)
                                .data(jobPostProfileDTO)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .errorCode(null)
                                .errorCode(null)
                                .build();
                return ResponseEntity.ok(apiResponse);
        }

        @PutMapping("/update-job/{id}")
        public ResponseEntity<ApiResponse<JobPostResponseDTO>> updateJob(@PathVariable String id,
                        @RequestBody JobPostUpdateDTO dto) {

                JobPostResponseDTO updatedJob = jobService.updateJob(UUID.fromString(id), dto);

                ApiResponse<JobPostResponseDTO> response = ApiResponse.<JobPostResponseDTO>builder()
                                .success(true)
                                .message(ResponseMessage.UPDATED)
                                .data(updatedJob)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .errorCode(null)
                                .errorDetails(null)
                                .build();

                return ResponseEntity.ok(response);
        }

        @DeleteMapping("/delete-job/{id}")
        public ResponseEntity<ApiResponse<Boolean>> deleteJob(@PathVariable UUID id) {
                jobService.deleteJob(id);
                ApiResponse<Boolean> apiResponse = ApiResponse.<Boolean>builder().data(true)
                                .success(true)
                                .status(HttpStatus.OK.value())
                                .message(ResponseMessage.DELETED)
                                .timestamp(LocalDateTime.now())
                                .errorCode(null)
                                .errorDetails(null)
                                .build();

                return ResponseEntity.ok(apiResponse);
        }

        @GetMapping("/all")
        public ResponseEntity<ApiResponse<List<JobPostResponseDTO>>> getAllCompanies() {
                List<JobPostResponseDTO> companies = jobService.getAllJobs();

                ApiResponse<List<JobPostResponseDTO>> apiResponse = ApiResponse.<List<JobPostResponseDTO>>builder()
                                .success(true)
                                .data(companies)
                                .status(HttpStatus.OK.value())
                                .message(companies.isEmpty()
                                                ? ResponseMessage.NO_DATA // ✅ अलग message अगर चाहो
                                                : ResponseMessage.FETCHED)
                                .timestamp(LocalDateTime.now())
                                .errorCode(null)
                                .errorDetails(null)
                                .build();

                return ResponseEntity.ok(apiResponse);
        }

        @GetMapping("/fetch-by-name")
        public ResponseEntity<ApiResponse<Page<JobPostSearchDTO>>> getJobsByName(
                        @RequestParam String title,
                        @RequestParam(defaultValue = "0") int pageNumber,
                        @RequestParam(defaultValue = "10") int pageSize,
                        @RequestParam(defaultValue = "id") String sortBy,
                        @RequestParam(defaultValue = "ASC") String sortDir) {

                Page<JobPostSearchDTO> jobs = jobService.getAllJobsByTitle(title,
                                pageNumber, pageSize,
                                sortBy,
                                sortDir);

                ApiResponse<Page<JobPostSearchDTO>> response = ApiResponse.<Page<JobPostSearchDTO>>builder()
                                .data(jobs)
                                .status(HttpStatus.OK.value())
                                .message(jobs.isEmpty()
                                                ? ResponseMessage.NO_DATA
                                                : ResponseMessage.FETCHED)
                                .timestamp(LocalDateTime.now())
                                .errorCode(null)
                                .errorDetails(null)
                                .build();

                return ResponseEntity.ok(response);
        }

}
