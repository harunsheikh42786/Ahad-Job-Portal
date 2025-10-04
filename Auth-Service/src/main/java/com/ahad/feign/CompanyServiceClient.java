package com.ahad.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ahad.helper.ApiResponse;
import com.ahad.helper.ApiVersion;

@FeignClient(name = "COMPANY-SERVICE")
public interface CompanyServiceClient {

    @GetMapping(ApiVersion.V1 + "/companies/verify")
    ResponseEntity<ApiResponse<Boolean>> verifyCompany(
            @RequestParam("email") String email,
            @RequestParam("password") String password);

    @GetMapping(ApiVersion.V1 + "/companies/exists")
    ResponseEntity<ApiResponse<Boolean>> checkCompanyExists(@RequestParam("email") String email);
}