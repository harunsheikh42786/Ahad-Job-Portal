package com.ahad.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.ahad.dto.CompanyProfileDTO;
import com.ahad.dto.CompanyResponseDTO;
import com.ahad.dto.CompanyUpdateDTO;
import com.ahad.helper.ApiResponse;
import com.ahad.messages.ResponseMessage;
import com.ahad.services.CompanyService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/companies") // plural is REST best practice
@RequiredArgsConstructor // Lombok constructor injection
public class CompanyController {

        private final CompanyService companyService;

        @GetMapping("/{companyId}")
        public ResponseEntity<ApiResponse<CompanyProfileDTO>> getCompanyById(@PathVariable String companyId) {
                CompanyProfileDTO companyProfileDTO = companyService.getCompanyById(companyId);

                ApiResponse<CompanyProfileDTO> apiResponse = ApiResponse.<CompanyProfileDTO>builder()
                                .success(true)
                                .message(ResponseMessage.FETCHED)
                                .data(companyProfileDTO)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .errorCode(null)
                                .errorCode(null)
                                .build();
                return ResponseEntity.ok(apiResponse);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<CompanyResponseDTO>> updateCompany(@PathVariable String id,
                        @RequestBody CompanyUpdateDTO dto) {

                CompanyResponseDTO updatedCompany = companyService.updateCompany(UUID.fromString(id), dto);

                ApiResponse<CompanyResponseDTO> response = ApiResponse.<CompanyResponseDTO>builder()
                                .success(true)
                                .message(ResponseMessage.UPDATED)
                                .data(updatedCompany)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .errorCode(null)
                                .errorDetails(null)
                                .build();

                return ResponseEntity.ok(response);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<Boolean>> deleteCompany(@PathVariable UUID id) {
                companyService.deleteCompany(id);
                ApiResponse<Boolean> apiResponse = ApiResponse.<Boolean>builder().data(true)
                                .status(HttpStatus.OK.value())
                                .message(ResponseMessage.DELETED)
                                .timestamp(LocalDateTime.now())
                                .errorCode(null)
                                .errorDetails(null)
                                .build();

                return ResponseEntity.ok(apiResponse);
        }

        @GetMapping
        public ResponseEntity<ApiResponse<List<CompanyResponseDTO>>> getAllCompanies() {
                List<CompanyResponseDTO> companies = companyService.getAllCompanies();

                ApiResponse<List<CompanyResponseDTO>> apiResponse = ApiResponse.<List<CompanyResponseDTO>>builder()
                                .data(companies)
                                .status(HttpStatus.OK.value())
                                .message(companies.isEmpty()
                                                ? ResponseMessage.NO_DATA
                                                : ResponseMessage.FETCHED)
                                .timestamp(LocalDateTime.now())
                                .errorCode(null)
                                .errorDetails(null)
                                .build();

                return ResponseEntity.ok(apiResponse);
        }

        // @GetMapping("/fetch-by-name")
        // public ResponseEntity<ApiResponse<Page<CompanySearchDTO>>> getCompanysByName(
        // @RequestParam String name,
        // @RequestParam(defaultValue = "0") int pageNumber,
        // @RequestParam(defaultValue = "10") int pageSize,
        // @RequestParam(defaultValue = "id") String sortBy,
        // @RequestParam(defaultValue = "ASC") String sortDir) {

        // Page<CompanySearchDTO> companys = companyService.getAllCompanysByName(name,
        // pageNumber, pageSize,
        // sortBy,
        // sortDir);

        // ApiResponse<Page<CompanySearchDTO>> response =
        // ApiResponse.<Page<CompanySearchDTO>>builder()
        // .data(companys)
        // .status(HttpStatus.OK.value())
        // .message(companys.isEmpty()
        // ? ResponseMessage.NO_COMPANYS_FOUND
        // : ResponseMessage.COMPANYS_FETCHED_SUCCESSFULLY)
        // .timestamp(LocalDateTime.now())
        // .errorCode(null)
        // .errorDetails(null)
        // .build();

        // return ResponseEntity.ok(response);
        // }

}
