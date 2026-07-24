package com.techvika.producer.service;

import com.techvika.producer.entity.User;
import com.techvika.producer.event.UserCreatedEvent;
import com.techvika.producer.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private KafkaTemplate<String, Object> kafkaTemplate;

    public UserServiceImpl(UserRepository userRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Long saveUser(User user) {
        log.info("UserServiceImpl::saveUser -> Invoked.");

        String messageId = UUID.randomUUID().toString();

        // Save User details into the database
        User savedUser = userRepository.save(user);

        log.info("UserServiceImpl::saveUser -> User saved in DB -> User first Name ={} & Email is = {}",
                savedUser.getFirstName(), savedUser.getEmail());

        // Create event object that will be published to the kafka topic
        UserCreatedEvent userCreatedEvent = new UserCreatedEvent(
                savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmail());

        // convert UserId into String to use it as kafka message key
        String userIdKey = String.valueOf(userCreatedEvent.getUserId());

        //create kafka ProducerRecord with Topic, key and Event data
        ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(
                "user-created-events-topic-1",
                userIdKey,
                userCreatedEvent);

        //Add custom header - MessageId to the kafka message
        producerRecord.headers().add("messageId", messageId.getBytes());

        //publish the message asynchronously to kafka
        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(producerRecord);

        // Call method executed after kafka sends the message
        future.whenComplete((result, e) -> {
            if (e != null) {
                log.info("Error while sending user Created Events", e);
            } else {
                log.info("UserServiceImpl::saveUser -> Event Metadata ={}", result.getRecordMetadata());
                log.info("UserServiceImpl::saveUser -> Event Partition ={}", result.getRecordMetadata().partition());
                log.info("UserServiceImpl::saveUser -> Event Topic ={}", result.getRecordMetadata().topic());
                log.info("UserServiceImpl::saveUser -> Event Offset ={}", result.getRecordMetadata().offset());
            }
        });

        return userCreatedEvent.getUserId();
    }
}
