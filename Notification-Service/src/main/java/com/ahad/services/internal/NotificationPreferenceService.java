package com.ahad.services.internal;

import java.util.UUID;

import com.ahad.models.NotificationPreference;

public interface NotificationPreferenceService {

    public NotificationPreference create(NotificationPreference notificationPreference);

    public NotificationPreference getById(UUID id);

    public NotificationPreference update(UUID id, NotificationPreference notificationPreference);

}
