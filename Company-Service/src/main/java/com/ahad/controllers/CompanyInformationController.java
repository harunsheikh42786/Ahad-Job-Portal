package com.ahad.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.ahad.dto.profile.CompanyInformationProfileDTO;
import com.ahad.dto.request.CompanyInformationRequestDTO;
import com.ahad.dto.response.CompanyInformationResponseDTO;
import com.ahad.dto.update.CompanyInformationUpdateDTO;
import com.ahad.helper.ApiResponse;
import com.ahad.helper.ApiVersion;
import com.ahad.messages.ResponseMessage;
import com.ahad.services.internal.CompanyInformationService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiVersion.V1 + "/company-information")
@RequiredArgsConstructor
public class CompanyInformationController {

        private final CompanyInformationService companyInformationService;

        @PostMapping("/{companyId}")
        public ResponseEntity<ApiResponse<CompanyInformationResponseDTO>> createCompanyInformation(
                        @PathVariable UUID companyId,
                        @RequestBody CompanyInformationRequestDTO dto) {

                CompanyInformationResponseDTO created = companyInformationService.createCompanyInformation(companyId,
                                dto);

                ApiResponse<CompanyInformationResponseDTO> response = ApiResponse
                                .<CompanyInformationResponseDTO>builder()
                                .success(true)
                                .message(ResponseMessage.CREATED)
                                .data(created)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.CREATED.value())
                                .build();

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<CompanyInformationProfileDTO>> getCompanyInformationById(
                        @PathVariable String id) {
                CompanyInformationProfileDTO info = companyInformationService.getCompanyInformationById(id);

                ApiResponse<CompanyInformationProfileDTO> response = ApiResponse.<CompanyInformationProfileDTO>builder()
                                .success(true)
                                .message(ResponseMessage.FETCHED)
                                .data(info)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();

                return ResponseEntity.ok(response);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<CompanyInformationResponseDTO>> updateCompanyInformation(
                        @PathVariable UUID id,
                        @RequestBody CompanyInformationUpdateDTO dto) {

                CompanyInformationResponseDTO updated = companyInformationService.updateCompanyInformation(id, dto);

                ApiResponse<CompanyInformationResponseDTO> response = ApiResponse
                                .<CompanyInformationResponseDTO>builder()
                                .success(true)
                                .message(ResponseMessage.UPDATED)
                                .data(updated)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();

                return ResponseEntity.ok(response);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<Boolean>> deleteCompanyInformation(@PathVariable UUID id) {
                companyInformationService.deleteCompanyInformation(id);

                ApiResponse<Boolean> response = ApiResponse.<Boolean>builder()
                                .success(true)
                                .message(ResponseMessage.DELETED)
                                .data(true)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();

                return ResponseEntity.ok(response);
        }

        @GetMapping
        public ResponseEntity<ApiResponse<List<CompanyInformationResponseDTO>>> getAllCompanyInformation() {
                List<CompanyInformationResponseDTO> list = companyInformationService.getAllCompanies();

                ApiResponse<List<CompanyInformationResponseDTO>> response = ApiResponse
                                .<List<CompanyInformationResponseDTO>>builder()
                                .success(true)
                                .message(list.isEmpty() ? ResponseMessage.NO_DATA : ResponseMessage.FETCHED)
                                .data(list)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();

                return ResponseEntity.ok(response);
        }
}
