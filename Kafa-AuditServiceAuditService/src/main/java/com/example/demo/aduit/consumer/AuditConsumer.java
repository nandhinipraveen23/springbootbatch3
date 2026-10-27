package com.example.demo.aduit.consumer;

import java.time.Instant;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.demo.audit.dto.CitizenRegisteredEvent;

import tools.jackson.databind.ObjectMapper;

@Service
public class AuditConsumer {

    private final ObjectMapper objectMapper;

    public AuditConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
        topics = "citizen-topic",
        groupId = "audit-group"
    )
    public void consume(String message) throws Exception {

        CitizenRegisteredEvent event =
            objectMapper.readValue(
                message, CitizenRegisteredEvent.class
            );

        System.out.println("=== AUDIT SERVICE ===");
        System.out.println("Event: CITIZEN_REGISTERED");
        System.out.println("Citizen ID: " + event.getId());
        System.out.println("Citizen Name: " + event.getName());
        System.out.println(
            "Vaccination Center ID: "
            + event.getVaccinCenterId()
        );
        System.out.println("Recorded At: " + Instant.now());
    }
}
