package com.example.demo.notification.consumer;

 

 
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.demo.notification.dto.CitizenRegisteredEvent;

import tools.jackson.databind.ObjectMapper;

@Service
public class NotificationConsumer {

    private final ObjectMapper objectMapper;

    public NotificationConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
        topics = "citizen-topic",
        groupId = "notification-group"
    )
    public void consume(String message) throws Exception {

        CitizenRegisteredEvent event =
            objectMapper.readValue(
                message, CitizenRegisteredEvent.class
            );

        System.out.println("=== NOTIFICATION SERVICE ===");
        System.out.println(
            "Sending registration notification to "
            + event.getName()
        );
        System.out.println(
            "Vaccination Center: "
            + event.getVaccinCenterId()
        );
        System.out.println("Notification processed!");
    }
}