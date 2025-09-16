package com.ahad.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ahad.enums.NotificationTarget;
import com.ahad.enums.NotificationType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequestDTO {

    @NotNull(message = "Notification type is required")
    private NotificationType type;

    @NotNull(message = "Notification target is required")
    private NotificationTarget target;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Message cannot be blank")
    private String message;

    private boolean isCompany; // primitive boolean, default false

    private boolean isRead; // primitive boolean, default false

    private boolean isBroadcast; // primitive boolean, default false

    private UUID receiverId; // optional for specific user/company
}
