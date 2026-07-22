package com.techvika.producer.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic userCreatedEventsTopic1(){
         return TopicBuilder.name("user-created-events-topic-1")
                 .partitions(3)
                 .replicas(3)
                 .configs(Map.of("min.insync.replicas","2"))
                .build();
    }
}
