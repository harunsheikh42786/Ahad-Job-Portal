package com.ahad.services.external;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ahad.dto.imports.UserForJobDTO;
import com.ahad.helper.ApiResponse;

@FeignClient(name = "USER-SERVICE", url = "http://localhost:8080/api/v1/users")
public interface UserService {
    @GetMapping("/job/{id}")
    ApiResponse<UserForJobDTO> getUserByIdForJob(@PathVariable UUID id);
}
