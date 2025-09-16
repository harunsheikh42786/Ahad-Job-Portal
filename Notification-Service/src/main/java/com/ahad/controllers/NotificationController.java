package com.ahad.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.ahad.dto.request.NotificationRequestDTO;
import com.ahad.dto.response.NotificationResponseDTO;
import com.ahad.dto.update.NotificationUpdateDTO;
import com.ahad.helper.ApiResponse;
import com.ahad.helper.ApiVersion;
import com.ahad.messages.ResponseMessage;
import com.ahad.services.internal.NotificationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(ApiVersion.V1 + "/notifications") // plural is REST best practice
@RequiredArgsConstructor // Lombok constructor injection
public class NotificationController {

        private final NotificationService notificationService;

        @PostMapping
        public ResponseEntity<ApiResponse<NotificationResponseDTO>> registerNotification(
                        @Valid @RequestBody NotificationRequestDTO notificationRequestDTO) {

                NotificationResponseDTO createdNotification = notificationService
                                .createNotification(notificationRequestDTO);

                ApiResponse<NotificationResponseDTO> apiResponse = ApiResponse
                                .<NotificationResponseDTO>builder()
                                .success(true)
                                .message(ResponseMessage.CREATED)
                                .data(createdNotification)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.CREATED.value())
                                .errorCode(null)
                                .errorDetails(null)
                                .build();

                return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }

        @GetMapping("/{referenceId}")
        public ResponseEntity<ApiResponse<List<NotificationResponseDTO>>> getNotificationByReferenceId(
                        @PathVariable String referenceId) {
                List<NotificationResponseDTO> notifications = notificationService
                                .getUnreadNotificationsByReceiverId(UUID.fromString(referenceId));

                ApiResponse<List<NotificationResponseDTO>> apiResponse = ApiResponse
                                .<List<NotificationResponseDTO>>builder()
                                .success(true)
                                .message(notifications.isEmpty() ? ResponseMessage.NO_DATA : ResponseMessage.FETCHED)
                                .data(notifications)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .errorCode(null)
                                .errorDetails(null)
                                .build();
                return ResponseEntity.ok(apiResponse);
        }

        @PutMapping("/{notificationId}")
        public ResponseEntity<ApiResponse<NotificationResponseDTO>> updateNotification(
                        @PathVariable String notificationId,
                        @RequestBody NotificationUpdateDTO dto) {

                NotificationResponseDTO updatedNotification = notificationService
                                .updateNotification(UUID.fromString(notificationId), dto);

                ApiResponse<NotificationResponseDTO> response = ApiResponse.<NotificationResponseDTO>builder()
                                .success(true)
                                .message(ResponseMessage.UPDATED)
                                .data(updatedNotification)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .errorCode(null)
                                .errorDetails(null)
                                .build();

                return ResponseEntity.ok(response);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<Boolean>> deleteNotification(@PathVariable UUID id) {
                notificationService.deleteNotification(id);
                ApiResponse<Boolean> apiResponse = ApiResponse.<Boolean>builder().data(true)
                                .success(true)
                                .status(HttpStatus.OK.value())
                                .message(ResponseMessage.DELETED)
                                .timestamp(LocalDateTime.now())
                                .errorCode(null)
                                .errorDetails(null)
                                .build();

                return ResponseEntity.ok(apiResponse);
        }

}
