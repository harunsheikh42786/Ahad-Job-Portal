// NotificationServiceImpl (updated with matching exceptions)
package com.ahad.servicesImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.ahad.exceptions.ResourceNotFoundException;
import com.ahad.messages.ResponseMessage;
import com.ahad.models.Notification;
import com.ahad.repos.NotificationRepository;
import com.ahad.services.NotificationService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getNotificationsByReferenceId(UUID id) {
        return notificationRepository.findByReferenceId(id);
    }

    @Override
    public Notification updateNotification(UUID id, Notification notification) {
        Notification fetchedNotification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification " + ResponseMessage.ID_NOT_FOUND + id));
        return notificationRepository.save(fetchedNotification);
    }

    @Override
    public void deleteNotification(UUID id) {
        Notification fetchedNotification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification " + ResponseMessage.ID_NOT_FOUND + id));
        notificationRepository.delete(fetchedNotification);
    }
}