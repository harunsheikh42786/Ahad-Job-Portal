package com.ahad.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic jobPostedTopic() {
        return TopicBuilder.name("job-posted")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic applicationSubmitTopic() {
        return TopicBuilder.name("job-application-submitted")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
