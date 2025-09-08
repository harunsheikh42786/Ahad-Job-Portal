package com.ahad.dto.request;

import com.ahad.enums.NotificationTarget;
import com.ahad.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class NotificationRequest {

    @NotNull(message = "Notification type is required")
    private NotificationType type;

    @NotNull(message = "Notification target is required")
    private NotificationTarget target;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Message cannot be blank")
    private String message;

    @NotNull(message = "Reference ID is required")
    private UUID referenceId;
}
