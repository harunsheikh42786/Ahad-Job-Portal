package com.ahad.dto.profile;

import com.ahad.enums.NotificationTarget;
import com.ahad.enums.NotificationType;
import lombok.Data;

import java.util.UUID;

@Data
public class NotificationProfileDTO {

    private UUID id;
    private NotificationType type;

    private NotificationTarget target;

    private String title;

    private String message;

    private boolean isRead;

    private UUID receiverId;
}
