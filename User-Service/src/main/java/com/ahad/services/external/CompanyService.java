package com.ahad.services.external;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ahad.dto.imports.CompanySearchDTO;
import com.ahad.helper.ApiResponse;

@FeignClient(value = "COMPANY-SERVICE", url = "http://localhost:8082/api/v1/companies")
public interface CompanyService {

    @GetMapping("/client/{companyId}")
    ApiResponse<CompanySearchDTO> getCompanyById(@PathVariable UUID companyId);

}
