package com.ahad.controllers;

import com.ahad.dto.request.UserInformationRequestDTO;
import com.ahad.dto.response.UserInformationResponseDTO;
import com.ahad.dto.update.UserInformationUpdateDTO;
import com.ahad.helper.ApiResponse;
import com.ahad.helper.ApiVersion;
import com.ahad.messages.ResponseMessage;
import com.ahad.services.UserInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiVersion.V1 + "/user-info")
@RequiredArgsConstructor
public class UserInformationController {

        private final UserInformationService userInformationService;

        @PostMapping
        public ResponseEntity<ApiResponse<UserInformationResponseDTO>> createUserInformation(
                        @RequestBody UserInformationRequestDTO dto) {
                UserInformationResponseDTO created = userInformationService.createUserInformation(dto);
                ApiResponse<UserInformationResponseDTO> response = ApiResponse.<UserInformationResponseDTO>builder()
                                .success(true)
                                .message("UserInformation " + ResponseMessage.CREATED)
                                .data(created)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.CREATED.value())
                                .build();
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<UserInformationResponseDTO>> getUserInformationById(@PathVariable UUID id) {
                UserInformationResponseDTO info = userInformationService.getUserInformationById(id);
                ApiResponse<UserInformationResponseDTO> response = ApiResponse.<UserInformationResponseDTO>builder()
                                .success(true)
                                .message("UserInformation "
                                                + ResponseMessage.FAILED)
                                .data(info)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();
                return ResponseEntity.ok(response);
        }

        @GetMapping("/user-id/{userId}")
        public ResponseEntity<ApiResponse<UserInformationResponseDTO>> getUserInformationByUserId(
                        @PathVariable UUID userId) {
                UserInformationResponseDTO info = userInformationService.getUserInformationByUserId(userId);
                ApiResponse<UserInformationResponseDTO> response = ApiResponse.<UserInformationResponseDTO>builder()
                                .success(true)
                                .message("UserInformation "
                                                + ResponseMessage.FAILED)
                                .data(info)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();
                return ResponseEntity.ok(response);
        }

        @GetMapping
        public ResponseEntity<ApiResponse<List<UserInformationResponseDTO>>> getAllUserInformation() {
                List<UserInformationResponseDTO> infos = userInformationService.getAllUserInformation();
                ApiResponse<List<UserInformationResponseDTO>> response = ApiResponse
                                .<List<UserInformationResponseDTO>>builder()
                                .success(true)
                                .message("UserInformations "
                                                + ResponseMessage.FETCHED)
                                .data(infos)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();
                return ResponseEntity.ok(response);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<UserInformationResponseDTO>> updateUserInformation(
                        @PathVariable UUID id,
                        @RequestBody UserInformationUpdateDTO dto) {
                UserInformationResponseDTO updated = userInformationService.updateUserInformation(id, dto);
                ApiResponse<UserInformationResponseDTO> response = ApiResponse.<UserInformationResponseDTO>builder()
                                .success(true)
                                .message("UserInformation "
                                                + ResponseMessage.UPDATED)
                                .data(updated)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();
                return ResponseEntity.ok(response);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<Void>> deleteUserInformation(@PathVariable UUID id) {
                userInformationService.deleteUserInformation(id);
                ApiResponse<Void> response = ApiResponse.<Void>builder()
                                .success(true)
                                .message("UserInformation "
                                                + ResponseMessage.DELETED)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();
                return ResponseEntity.ok(response);
        }
}
