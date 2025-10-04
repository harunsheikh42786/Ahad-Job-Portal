package com.ahad.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ahad.helper.ApiResponse;
import com.ahad.helper.ApiVersion;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {

    @GetMapping(ApiVersion.V1 + "/users/verify")
    ResponseEntity<ApiResponse<Boolean>> verifyUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password);

    @GetMapping(ApiVersion.V1 + "/users/exists")
    ResponseEntity<ApiResponse<Boolean>> checkUserExists(@RequestParam("email") String email);
}
