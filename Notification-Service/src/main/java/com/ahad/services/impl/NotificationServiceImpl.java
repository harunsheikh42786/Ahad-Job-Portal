package com.ahad.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ahad.dto.request.NotificationRequestDTO;
import com.ahad.dto.response.NotificationResponseDTO;
import com.ahad.dto.update.NotificationUpdateDTO;
import com.ahad.exceptions.ResourceNotFoundException;
import com.ahad.exceptions.MappingFailedException;
import com.ahad.mapper.NotificationMapper;
import com.ahad.messages.ResponseMessage;
import com.ahad.models.Notification;
import com.ahad.repos.NotificationRepository;
import com.ahad.services.internal.NotificationService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public NotificationResponseDTO createNotification(NotificationRequestDTO dto) {
        Notification notification = notificationMapper.toEntity(dto);
        notification.setCreatedAt(LocalDateTime.now());
        Notification savedNotification = notificationRepository.save(notification);
        return Optional.ofNullable(notificationMapper.toResponseDto(savedNotification))
                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));
    }

    @Override
    public List<NotificationResponseDTO> getUnreadNotificationsByReceiverId(UUID receiverId) {
        List<Notification> notifications = notificationRepository
                .findByReceiverIdAndIsReadFalseOrIsBroadcastTrueOrderByCreatedAtDesc(receiverId);
        if (notifications.isEmpty()) {
            throw new ResourceNotFoundException("Notifications " + ResponseMessage.ID_NOT_FOUND + receiverId);
        }
        return notifications.stream()
                .map(n -> Optional.ofNullable(notificationMapper.toResponseDto(n))
                        .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED)))
                .toList();
    }

    @Override
    public NotificationResponseDTO updateNotification(UUID notificationId, NotificationUpdateDTO dto) {
        Notification fetchedNotification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Notification " + ResponseMessage.ID_NOT_FOUND + notificationId));

        notificationMapper.toEntity(dto, fetchedNotification);
        Notification updatedNotification = notificationRepository.save(fetchedNotification);

        return Optional.ofNullable(notificationMapper.toResponseDto(updatedNotification))
                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));
    }

    @Override
    public void deleteNotification(UUID notificationId) {
        Notification fetchedNotification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Notification " + ResponseMessage.ID_NOT_FOUND + notificationId));

        notificationRepository.delete(fetchedNotification);
    }
}
