package com.ahad.services.external;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ahad.dto.imports.UserForJobDTO;
import com.ahad.helper.ApiResponse;
import com.ahad.helper.ApiVersion;

@FeignClient(name = "USER-SERVICE")
public interface UserService {
    @GetMapping(ApiVersion.V1 + "/users/job/{id}")
    ApiResponse<UserForJobDTO> getUserByIdForJob(@PathVariable UUID id);
}
