package com.ahad.servicesImpl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ahad.exceptions.ResourceNotFoundException;
import com.ahad.messages.ResponseMessage;
import com.ahad.models.NotificationPreference;
import com.ahad.repos.NotificationPreferenceRepository;
import com.ahad.services.NotificationPreferenceService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationPreferenceServiceImpl implements NotificationPreferenceService {

    private final NotificationPreferenceRepository notificationPreferenceRepository;

    @Override
    public NotificationPreference create(NotificationPreference notificationPreference) {
        return notificationPreferenceRepository.save(notificationPreference);
    }

    @Override
    public NotificationPreference getById(UUID id) {
        return notificationPreferenceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("NotificationPreference " + ResponseMessage.ID_NOT_FOUND + id));
    }

    @Override
    public NotificationPreference update(UUID id, NotificationPreference notificationPreference) {
        NotificationPreference fetchedNotificationPreference = getById(id);
        return notificationPreferenceRepository.save(fetchedNotificationPreference);
    }

}
