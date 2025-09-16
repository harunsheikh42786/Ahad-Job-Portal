package com.ahad.dto.response;

import com.ahad.enums.NotificationTarget;
import com.ahad.enums.NotificationType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class NotificationResponseDTO {
    private UUID id;
    private NotificationType type;
    private NotificationTarget target;
    private String title;
    private String message;
    private LocalDateTime createdAt;
    private boolean isRead;
    private UUID receiverId;
}
