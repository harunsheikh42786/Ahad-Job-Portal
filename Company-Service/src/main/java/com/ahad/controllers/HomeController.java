package com.ahad.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ahad.dto.CompanyRequestDTO;
import com.ahad.dto.CompanyResponseDTO;
import com.ahad.helper.ApiResponse;
import com.ahad.messages.ResponseMessage;
import com.ahad.services.CompanyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/companies")
public class HomeController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<ApiResponse<CompanyResponseDTO>> registerCompany(
            @Valid @RequestBody CompanyRequestDTO companyRequestDTO) {
        CompanyResponseDTO createdCompany = companyService.createCompany(companyRequestDTO);

        ApiResponse<CompanyResponseDTO> apiResponse = ApiResponse.<CompanyResponseDTO>builder()
                .success(true)
                .message(ResponseMessage.CREATED)
                .data(createdCompany)
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value())
                .errorCode(null)
                .errorDetails(null)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

}
