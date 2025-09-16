package com.ahad.services.external;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ahad.dto.imports.JobPostForCompanyDTO;
import com.ahad.helper.ApiResponse;

@FeignClient(name = "JOB-SERVICE")
public interface JobService {

    @GetMapping("/user/all/{userId}")
    ApiResponse<List<JobPostForCompanyDTO>> getJobsByUserId(@PathVariable UUID userId);

    @GetMapping("/company/all/{companyId}")
    ApiResponse<List<JobPostForCompanyDTO>> getJobsByCompanyId(@PathVariable UUID companyId);

}
