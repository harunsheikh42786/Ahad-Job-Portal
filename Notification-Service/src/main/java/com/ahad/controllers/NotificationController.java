package com.ahad.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.ahad.helper.ApiResponse;
import com.ahad.messages.ResponseMessage;
import com.ahad.models.Notification;
import com.ahad.services.NotificationService;

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
@RequestMapping("/notifications") // plural is REST best practice
@RequiredArgsConstructor // Lombok constructor injection
public class NotificationController {

        private final NotificationService notificationService;

        @PostMapping
        public ResponseEntity<ApiResponse<Notification>> registerNotification(
                        @Valid @RequestBody Notification notification) {

                Notification createdNotification = notificationService
                                .createNotification(notification);

                ApiResponse<Notification> apiResponse = ApiResponse
                                .<Notification>builder()
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

        @GetMapping("/get/{notificationId}")
        public ResponseEntity<ApiResponse<List<Notification>>> getNotificationById(
                        @PathVariable String notificationId) {
                List<Notification> notifications = notificationService
                                .getNotificationsByReferenceId(UUID.fromString(notificationId));

                ApiResponse<List<Notification>> apiResponse = ApiResponse.<List<Notification>>builder()
                                .success(true)
                                .message(ResponseMessage.FETCHED)
                                .data(notifications)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .errorCode(null)
                                .errorCode(null)
                                .build();
                return ResponseEntity.ok(apiResponse);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<Notification>> updateNotification(@PathVariable String id,
                        @RequestBody Notification dto) {

                Notification updatedNotification = notificationService
                                .updateNotification(UUID.fromString(id), dto);

                ApiResponse<Notification> response = ApiResponse.<Notification>builder()
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
