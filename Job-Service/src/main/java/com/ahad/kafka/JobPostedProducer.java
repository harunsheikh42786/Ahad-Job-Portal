package com.ahad.kafka;

import com.ahad.events.JobPostedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobPostedProducer {

    private final KafkaTemplate<String, JobPostedEvent> kafkaTemplate;

    public void sendJobPostedEvent(JobPostedEvent event) {
        kafkaTemplate.send("job-posted", event.getId().toString(), event);
        System.out.println("✅ JobPostedEvent sent: " + event);
    }
}
