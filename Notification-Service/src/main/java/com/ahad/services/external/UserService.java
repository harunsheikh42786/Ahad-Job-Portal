package com.ahad.services.external;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.ahad.helper.ApiVersion;

@FeignClient(name = "USER-SERVICE")
public interface UserService {

    @GetMapping(ApiVersion.V1 + "/job-History/by-heading")
    List<UUID> getAllUserIdsByHeading(String jobTitle);

    // @PostMapping("/notify")
    // void notifyUser(Notification notification);
}
