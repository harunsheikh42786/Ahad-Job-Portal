package com.ahad.controllers;

import com.ahad.dto.request.JobHistoryRequestDTO;
import com.ahad.dto.response.JobHistoryResponseDTO;
import com.ahad.dto.update.JobHistoryUpdateDTO;
import com.ahad.helper.ApiResponse;
import com.ahad.helper.ApiVersion;
import com.ahad.messages.ResponseMessage;
import com.ahad.services.JobHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiVersion.V1 + "/job-history")
public class JobHistoryController {

        @Autowired
        private JobHistoryService jobHistoryService;

        // ✅ Add Job History
        @PostMapping
        public ResponseEntity<ApiResponse<JobHistoryResponseDTO>> addJobHistory(
                        @RequestBody JobHistoryRequestDTO jobHistoryRequestDTO) {

                JobHistoryResponseDTO createdJobHistory = jobHistoryService.addJobHistory(jobHistoryRequestDTO);

                ApiResponse<JobHistoryResponseDTO> response = ApiResponse.<JobHistoryResponseDTO>builder()
                                .success(true)
                                .message("JobHistory " + ResponseMessage.CREATED)
                                .data(createdJobHistory)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.CREATED.value())
                                .errorCode(null)
                                .errorDetails(null)
                                .build();

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        // ✅ Get Job History by ID
        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<JobHistoryResponseDTO>> getJobHistoryById(@PathVariable UUID id) {
                JobHistoryResponseDTO jobHistory = jobHistoryService.getJobHistoryById(id);

                ApiResponse<JobHistoryResponseDTO> response = ApiResponse.<JobHistoryResponseDTO>builder()
                                .success(true)
                                .message("JobHistory "
                                                + ResponseMessage.NOT_FOUND)
                                .data(jobHistory)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .errorCode(null)
                                .errorDetails(null)
                                .build();

                return ResponseEntity.ok(response);
        }

        // ✅ Get All Job Histories
        @GetMapping("/{userInformationId}")
        public ResponseEntity<ApiResponse<List<JobHistoryResponseDTO>>> getAllJobHistories(
                        @PathVariable UUID userInformationId) {
                List<JobHistoryResponseDTO> jobHistories = jobHistoryService
                                .getAllJobHistoryByUserInformationId(userInformationId);

                ApiResponse<List<JobHistoryResponseDTO>> response = ApiResponse.<List<JobHistoryResponseDTO>>builder()
                                .success(true)
                                .message("JobHistories "
                                                + ResponseMessage.FETCHED)
                                .data(jobHistories)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .errorCode(null)
                                .errorDetails(null)
                                .build();

                return ResponseEntity.ok(response);
        }

        // ✅ Update Job History
        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<JobHistoryResponseDTO>> updateJobHistory(
                        @PathVariable UUID id,
                        @RequestBody JobHistoryUpdateDTO jobHistoryUpdateDTO) {

                JobHistoryResponseDTO updatedJobHistory = jobHistoryService.updateJobHistory(id, jobHistoryUpdateDTO);

                ApiResponse<JobHistoryResponseDTO> response = ApiResponse.<JobHistoryResponseDTO>builder()
                                .success(true)
                                .message("JobHistory "
                                                + ResponseMessage.UPDATED)
                                .data(updatedJobHistory)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .errorCode(null)
                                .errorDetails(null)
                                .build();

                return ResponseEntity.ok(response);
        }

        // ✅ Delete Job History
        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<Void>> deleteJobHistory(@PathVariable UUID id) {
                jobHistoryService.deleteJobHistory(id);

                ApiResponse<Void> response = ApiResponse.<Void>builder()
                                .success(true)
                                .message("JobHistory "
                                                + ResponseMessage.DELETED)
                                .data(null)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .errorCode(null)
                                .errorDetails(null)
                                .build();

                return ResponseEntity.ok(response);
        }
}
