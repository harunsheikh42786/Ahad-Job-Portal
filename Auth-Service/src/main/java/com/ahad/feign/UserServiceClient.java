package com.ahad.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ahad.helper.ApiResponse;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {

    @GetMapping("/api/v1/users/verify")
    ApiResponse<Boolean> verifyUser(@RequestParam String username, @RequestParam String password);
}
