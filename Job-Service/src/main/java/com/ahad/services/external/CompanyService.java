package com.ahad.services.external;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ahad.dto.imports.CompanyForJobDTO;
import com.ahad.helper.ApiResponse;

// @FeignClient(name = "COMPANY-SERVICE", url = "http://localhost:8082/api/v1/companies") // If there is no eureka server
@FeignClient(name = "COMPANY-SERVICE")
public interface CompanyService {
    @GetMapping("/client/{id}")
    ApiResponse<CompanyForJobDTO> getCompanyByIdForJob(@PathVariable UUID id);
}
