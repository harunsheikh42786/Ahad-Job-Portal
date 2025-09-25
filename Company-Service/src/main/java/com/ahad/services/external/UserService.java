package com.ahad.services.external;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ahad.dto.imports.UserSearchForCompanyhDTO;
import com.ahad.helper.ApiResponse;
import com.ahad.helper.ApiVersion;

@FeignClient(name = "USER-SERVICE")
public interface UserService {

    @GetMapping(ApiVersion.V1 + "/users/company/all/{companyId}")
    ApiResponse<List<UserSearchForCompanyhDTO>> getUsersByCompanyId(@PathVariable UUID companyId);

}
