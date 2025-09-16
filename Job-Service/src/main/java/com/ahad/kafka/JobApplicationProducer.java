package com.ahad.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ahad.events.JobApplicationEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobApplicationProducer {

    private final KafkaTemplate<String, JobApplicationEvent> kafkaTemplate;

    public void publishJobApplicationEvent(JobApplicationEvent event) {
        kafkaTemplate.send("job-application-submitted", event);
        System.out.println("✅ Published Job Application Event: " + event.getJobTitle() + " by Applicant ID: "
                + event.getApplicantId());
    }

}
