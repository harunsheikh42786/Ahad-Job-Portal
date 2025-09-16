package com.ahad.dto.update;

import com.ahad.enums.NotificationTarget;
import com.ahad.enums.NotificationType;
import lombok.Data;

@Data
public class NotificationUpdateDTO {

    private NotificationType type;

    private NotificationTarget target;

    private String title;

    private boolean isCompany;

    private String message;

    private boolean isRead;

}
