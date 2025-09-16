package com.ahad.controllers;

import com.ahad.dto.application.JobApplicationRequestDTO;
import com.ahad.dto.application.JobApplicationResponseDTO;
import com.ahad.dto.application.JobApplicationUpdateDTO;
import com.ahad.helper.ApiResponse;
import com.ahad.helper.ApiVersion;
import com.ahad.messages.ResponseMessage;
import com.ahad.services.internal.JobApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiVersion.V1 + "/job/applications")
@RequiredArgsConstructor
public class JobApplicationController {

        private final JobApplicationService jobApplicationService;

        // ✅ Apply for a Job
        @PostMapping
        public ResponseEntity<ApiResponse<JobApplicationResponseDTO>> applyForJob(
                        @Valid @RequestBody JobApplicationRequestDTO request) {

                JobApplicationResponseDTO created = jobApplicationService.applyForJob(request);

                ApiResponse<JobApplicationResponseDTO> response = ApiResponse.<JobApplicationResponseDTO>builder()
                                .success(true)
                                .message(ResponseMessage.CREATED)
                                .data(created)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.CREATED.value())
                                .build();

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        // ✅ Update Application
        @PutMapping("/{applicationId}")
        public ResponseEntity<ApiResponse<JobApplicationResponseDTO>> updateJobApplication(
                        @PathVariable UUID applicationId,
                        @Valid @RequestBody JobApplicationUpdateDTO request) {

                JobApplicationResponseDTO updated = jobApplicationService.updateJobApplication(applicationId, request);

                ApiResponse<JobApplicationResponseDTO> response = ApiResponse.<JobApplicationResponseDTO>builder()
                                .success(true)
                                .message(ResponseMessage.UPDATED)
                                .data(updated)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();

                return ResponseEntity.ok(response);
        }

        // ✅ Delete Application
        @DeleteMapping("/{applicationId}")
        public ResponseEntity<ApiResponse<Boolean>> deleteJobApplication(@PathVariable UUID applicationId) {
                jobApplicationService.deleteJobApplication(applicationId);

                ApiResponse<Boolean> response = ApiResponse.<Boolean>builder()
                                .success(true)
                                .message(ResponseMessage.DELETED)
                                .data(true)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();

                return ResponseEntity.ok(response);
        }

        // ✅ Get All Applications by User
        @GetMapping("/user/{userId}")
        public ResponseEntity<ApiResponse<List<JobApplicationResponseDTO>>> getApplicationsByUser(
                        @PathVariable UUID userId) {

                List<JobApplicationResponseDTO> apps = jobApplicationService.getJobApplicationsByUserId(userId);

                ApiResponse<List<JobApplicationResponseDTO>> response = ApiResponse
                                .<List<JobApplicationResponseDTO>>builder()
                                .success(true)
                                .message(apps.isEmpty() ? ResponseMessage.NO_DATA : ResponseMessage.FETCHED)
                                .data(apps)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();

                return ResponseEntity.ok(response);
        }

        // ✅ Get All Applications by Job
        @GetMapping("/post/{jobPostId}")
        public ResponseEntity<ApiResponse<List<JobApplicationResponseDTO>>> getApplicationsByJob(
                        @PathVariable UUID jobPostId) {

                List<JobApplicationResponseDTO> apps = jobApplicationService.getJobApplicationsByJobId(jobPostId);

                ApiResponse<List<JobApplicationResponseDTO>> response = ApiResponse
                                .<List<JobApplicationResponseDTO>>builder()
                                .success(true)
                                .message(apps.isEmpty() ? ResponseMessage.NO_DATA : ResponseMessage.FETCHED)
                                .data(apps)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();

                return ResponseEntity.ok(response);
        }
}
