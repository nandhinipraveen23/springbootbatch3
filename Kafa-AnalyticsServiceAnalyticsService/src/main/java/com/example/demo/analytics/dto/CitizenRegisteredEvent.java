 package com.example.demo.analytics.dto;

public class CitizenRegisteredEvent {

    private int id;
    private String name;
    private int vaccinCenterId;

    public CitizenRegisteredEvent() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVaccinCenterId() {
        return vaccinCenterId;
    }

    public void setVaccinCenterId(int vaccinCenterId) {
        this.vaccinCenterId = vaccinCenterId;
    }
}