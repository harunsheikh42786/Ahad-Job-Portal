package com.ahad.services.internal;

import java.util.List;
import java.util.UUID;

import com.ahad.dto.request.NotificationRequestDTO;
import com.ahad.dto.response.NotificationResponseDTO;
import com.ahad.dto.update.NotificationUpdateDTO;

public interface NotificationService {

    // Create a new notification
    NotificationResponseDTO createNotification(NotificationRequestDTO dto);

    // Get single notification by ID
    List<NotificationResponseDTO> getUnreadNotificationsByReceiverId(UUID id);

    // Update existing notification
    NotificationResponseDTO updateNotification(UUID id, NotificationUpdateDTO dto);

    // Delete notification by ID
    void deleteNotification(UUID id);

}
