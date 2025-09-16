package com.ahad.kafka;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ahad.dto.request.NotificationRequestDTO;
import com.ahad.dto.response.NotificationResponseDTO;
import com.ahad.enums.NotificationTarget;
import com.ahad.enums.NotificationType;
import com.ahad.events.JobPostedEvent;
import com.ahad.mapper.NotificationMapper;
import com.ahad.messages.NotificationMessages;
import com.ahad.models.Notification;
import com.ahad.repos.NotificationRepository;
import com.ahad.services.internal.NotificationService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "job-posted", groupId = "job-portal-group")
    public void consume(JobPostedEvent event) {

        // ✅ Create DTO
        NotificationRequestDTO requestDTO = NotificationRequestDTO.builder()
                .type(NotificationType.JOB_UPDATED) // fixed type
                .target(NotificationTarget.USER) // all users
                .title("New Job Posted: " + event.getTitle())
                .message(NotificationMessages.newJobPosted(event.getTitle(), event.getLocation()))
                .isCompany(false)
                .isRead(false)
                .isBroadcast(true)
                .receiverId(null) // broadcast -> no specific user
                .build();

        notificationService.createNotification(requestDTO);

        System.out.println("✅ Consumed Job Event: " + event.getTitle() + " at " + event.getLocation());
    }

    @KafkaListener(topics = "job-application-submitted", groupId = "job-portal-group")
    public void consumeJobApplicationEvent(com.ahad.events.JobApplicationEvent event) {
        System.out.println("Consuming Job Application Event: " + event);
        // ✅ Create DTO
        NotificationRequestDTO requestDTO = NotificationRequestDTO.builder()
                .type(NotificationType.APPLICATION_SUBMITTED) // fixed type
                .target(NotificationTarget.COMPANY) // all companies
                .title("New Application for: " + event.getJobTitle())
                .message(NotificationMessages.newApplication(event.getJobTitle()))
                .isCompany(true)
                .isRead(false)
                .isBroadcast(false) // specific company
                .receiverId(event.getCompanyId()) // specific company
                .build();

        notificationService.createNotification(requestDTO);

        System.out.println("✅ Consumed Job Application Event: " + event.getJobTitle() + " by Applicant ID: "
                + event.getApplicantId());
    }
}
