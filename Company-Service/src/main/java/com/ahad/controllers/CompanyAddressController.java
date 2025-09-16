package com.ahad.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.ahad.dto.profile.CompanyAddressProfileDTO;
import com.ahad.dto.request.CompanyAddressRequestDTO;
import com.ahad.dto.response.CompanyAddressResponseDTO;
import com.ahad.dto.update.CompanyAddressUpdateDTO;
import com.ahad.helper.ApiResponse;
import com.ahad.helper.ApiVersion;
import com.ahad.messages.ResponseMessage;
import com.ahad.services.internal.CompanyAddressService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiVersion.V1 + "/company-addresses") // plural naming convention
@RequiredArgsConstructor
public class CompanyAddressController {

    private final CompanyAddressService companyAddressService;

    @PostMapping("/{companyInfoId}")
    public ResponseEntity<ApiResponse<CompanyAddressResponseDTO>> createCompanyAddress(
            @PathVariable UUID companyInfoId,
            @RequestBody CompanyAddressRequestDTO dto) {

        CompanyAddressResponseDTO created = companyAddressService.createCompanyAddress(companyInfoId, dto);

        ApiResponse<CompanyAddressResponseDTO> response = ApiResponse.<CompanyAddressResponseDTO>builder()
                .success(true)
                .message(ResponseMessage.CREATED)
                .data(created)
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyAddressProfileDTO>> getCompanyAddressById(@PathVariable String id) {
        CompanyAddressProfileDTO address = companyAddressService.getCompanyAddressById(id);

        ApiResponse<CompanyAddressProfileDTO> response = ApiResponse.<CompanyAddressProfileDTO>builder()
                .success(true)
                .message(ResponseMessage.FETCHED)
                .data(address)
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyAddressResponseDTO>> updateCompanyAddress(
            @PathVariable UUID id,
            @RequestBody CompanyAddressUpdateDTO dto) {

        CompanyAddressResponseDTO updated = companyAddressService.updateCompanyAddress(id, dto);

        ApiResponse<CompanyAddressResponseDTO> response = ApiResponse.<CompanyAddressResponseDTO>builder()
                .success(true)
                .message(ResponseMessage.UPDATED)
                .data(updated)
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteCompanyAddress(@PathVariable UUID id) {
        companyAddressService.deleteCompanyAddress(id);

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
    public ResponseEntity<ApiResponse<List<CompanyAddressResponseDTO>>> getAllCompanyAddresses() {
        List<CompanyAddressResponseDTO> list = companyAddressService.getAllCompanyAddresses();

        ApiResponse<List<CompanyAddressResponseDTO>> response = ApiResponse.<List<CompanyAddressResponseDTO>>builder()
                .success(true)
                .message(list.isEmpty() ? ResponseMessage.NO_DATA : ResponseMessage.FETCHED)
                .data(list)
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(response);
    }
}
