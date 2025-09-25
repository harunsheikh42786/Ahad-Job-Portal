package com.ahad.controllers;

import com.ahad.dto.request.EducationRequestDTO;
import com.ahad.dto.update.EducationUpdateDTO;
import com.ahad.dto.response.EducationResponseDTO;
import com.ahad.helper.ApiResponse;
import com.ahad.helper.ApiVersion;
import com.ahad.messages.ResponseMessage;
import com.ahad.services.internal.EducationService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiVersion.V1 + "/educations")
@RequiredArgsConstructor
public class EducationController {

        private final EducationService educationService;

        // ✅ Add Education
        @PostMapping
        public ResponseEntity<ApiResponse<EducationResponseDTO>> addEducation(
                        @Valid @RequestBody EducationRequestDTO educationRequestDTO) {

                EducationResponseDTO createdEducation = educationService.addEducation(educationRequestDTO);

                ApiResponse<EducationResponseDTO> apiResponse = ApiResponse.<EducationResponseDTO>builder()
                                .success(true)
                                .message("Education " + ResponseMessage.CREATED)
                                .data(createdEducation)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.CREATED.value())
                                .errorCode(null)
                                .errorDetails(null)
                                .build();

                return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }

        // ✅ Get All Educations
        @GetMapping
        public ResponseEntity<ApiResponse<List<EducationResponseDTO>>> getAllEducations(
                        @PathVariable UUID userId) {

                List<EducationResponseDTO> educations = educationService.getAllEducations(userId);

                ApiResponse<List<EducationResponseDTO>> apiResponse = ApiResponse.<List<EducationResponseDTO>>builder()
                                .success(true)
                                .message(ResponseMessage.FETCHED)
                                .data(educations)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .errorCode(null)
                                .errorDetails(null)
                                .build();

                return ResponseEntity.ok(apiResponse);
        }

        // ✅ Get Education by ID
        @GetMapping("/{educationId}")
        public ResponseEntity<ApiResponse<EducationResponseDTO>> getEducationById(
                        @PathVariable UUID educationId) {

                EducationResponseDTO education = educationService.getEducationById(educationId);

                ApiResponse<EducationResponseDTO> apiResponse = ApiResponse.<EducationResponseDTO>builder()
                                .success(true)
                                .message("Educations "
                                                + ResponseMessage.FETCHED)
                                .data(education)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .errorCode(null)
                                .errorDetails(null)
                                .build();

                return ResponseEntity.ok(apiResponse);
        }

        // ✅ Update Education
        @PutMapping("/{educationId}")
        public ResponseEntity<ApiResponse<EducationResponseDTO>> updateEducation(
                        @PathVariable UUID educationId,
                        @Valid @RequestBody EducationUpdateDTO educationUpdateDTO) {

                EducationResponseDTO updatedEducation = educationService.updateEducation(educationId,
                                educationUpdateDTO);

                ApiResponse<EducationResponseDTO> apiResponse = ApiResponse.<EducationResponseDTO>builder()
                                .success(true)
                                .message("Education "
                                                + ResponseMessage.UPDATED)
                                .data(updatedEducation)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .errorCode(null)
                                .errorDetails(null)
                                .build();

                return ResponseEntity.ok(apiResponse);
        }

        // ✅ Delete Education
        @DeleteMapping("/{educationId}")
        public ResponseEntity<ApiResponse<Boolean>> deleteEducation(
                        @PathVariable UUID educationId) {

                educationService.deleteEducation(educationId);

                ApiResponse<Boolean> apiResponse = ApiResponse.<Boolean>builder()
                                .success(true)
                                .message("Education "
                                                + ResponseMessage.DELETED)
                                .data(true)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.NO_CONTENT.value())
                                .errorCode(null)
                                .errorDetails(null)
                                .build();

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse);
        }
}
