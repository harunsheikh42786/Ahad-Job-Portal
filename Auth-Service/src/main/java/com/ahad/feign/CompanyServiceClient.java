package com.ahad.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ahad.helper.ApiResponse;

@FeignClient(name = "COMPANY-SERVICE")
public interface CompanyServiceClient {

    @GetMapping("/api/v1/companies/verify")
    ApiResponse<Boolean> verifyCompany(@RequestParam String username, @RequestParam String password);
}
