package com.ahad.services.external;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ahad.dto.imports.JobPostForUserDTO;
import com.ahad.helper.ApiResponse;

@FeignClient(value = "JOB-SERVICE")
public interface JobService {
    @GetMapping("/api/v1/jobs/user/all/{userId}")
    ApiResponse<List<JobPostForUserDTO>> getJobsByUserId(@PathVariable UUID userId);

}
