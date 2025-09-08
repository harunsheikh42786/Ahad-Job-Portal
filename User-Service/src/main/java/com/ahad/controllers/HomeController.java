package com.ahad.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ahad.dto.request.UserRequestDTO;
import com.ahad.dto.response.UserResponseDTO;
import com.ahad.helper.ApiResponse;
import com.ahad.helper.ApiVersion;
import com.ahad.messages.ResponseMessage;
import com.ahad.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiVersion.V1 + "/users")
public class HomeController {

        @Autowired
        private UserService userService;

        @PostMapping("/register")
        public ResponseEntity<ApiResponse<UserResponseDTO>> registerUser(
                        @Valid @RequestBody UserRequestDTO userRequestDTO) {
                UserResponseDTO createdUser = userService.createUser(userRequestDTO);

                ApiResponse<UserResponseDTO> apiResponse = ApiResponse.<UserResponseDTO>builder()
                                .success(true)
                                .message("User "
                                                + ResponseMessage.CREATED)
                                .data(createdUser)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.CREATED.value())
                                .errorCode(null)
                                .errorDetails(null)
                                .build();

                return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }

}
