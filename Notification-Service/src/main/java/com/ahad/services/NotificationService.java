package com.ahad.services;

import java.util.List;
import java.util.UUID;
import com.ahad.models.Notification;

public interface NotificationService {

    // Create a new notification
    Notification createNotification(Notification notification);

    // Get single notification by ID
    List<Notification> getNotificationsByReferenceId(UUID id);

    // Update existing notification
    Notification updateNotification(UUID id, Notification notification);

    // Delete notification by ID
    void deleteNotification(UUID id);

}
