package com.ahad.services.external;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "USER-SERVICE", url = "http://localhost:8081/api/v1/users")
public interface UserService {

    @GetMapping("/by-heading")
    List<UUID> getAllUserIdsByHeading(String jobTitle);

    // @PostMapping("/notify")
    // void notifyUser(Notification notification);
}
