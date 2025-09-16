package com.ahad.repos;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ahad.models.Notification;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    // User ke liye, including broadcast notifications
    List<Notification> findByReceiverIdOrIsBroadcastTrueOrderByCreatedAtDesc(UUID receiverId);

    // Agar sirf unread notifications chahiye
    List<Notification> findByReceiverIdAndIsReadFalseOrIsBroadcastTrueOrderByCreatedAtDesc(UUID receiverId);
}
