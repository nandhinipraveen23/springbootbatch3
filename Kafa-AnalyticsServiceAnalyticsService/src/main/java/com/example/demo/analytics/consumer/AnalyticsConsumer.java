package com.example.demo.analytics.consumer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

 

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.demo.analytics.dto.CitizenRegisteredEvent;

import tools.jackson.databind.ObjectMapper;

@Service
public class AnalyticsConsumer {

    private final ObjectMapper objectMapper;

    private final ConcurrentHashMap<Integer, AtomicInteger>
        centerCounts = new ConcurrentHashMap<>();

    public AnalyticsConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
        topics = "citizen-topic",
        groupId = "analytics-group"
    )
    public void consume(String message) throws Exception {

        CitizenRegisteredEvent event =
            objectMapper.readValue(
                message, CitizenRegisteredEvent.class
            );

        int centerId = event.getVaccinCenterId();

        int count = centerCounts
            .computeIfAbsent(centerId, id -> new AtomicInteger())
            .incrementAndGet();

        System.out.println("=== ANALYTICS SERVICE ===");
        System.out.println("Citizen: " + event.getName());
        System.out.println("Center ID: " + centerId);
        System.out.println(
            "Total registrations for center "
            + centerId + ": " + count
        );
    }
    
    // 2. Receive messages from Vaccination Service
    @KafkaListener(
        topics = "vaccination-topic",
        groupId = "analytics-group"
    )
    public void consumeVaccination(String message) {

        System.out.println("Vaccination message received!");

        System.out.println("=== VACCINATION NOTIFICATION ===");
        System.out.println("Vaccination Data: " + message);
        System.out.println("Vaccination notification processed!");
    }
}