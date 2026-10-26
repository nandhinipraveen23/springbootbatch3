package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Vaccination {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String centerName;
	private String address;
	private Integer vaccinCenterId;
	public Vaccination() {}
	
	public Vaccination(Integer id, String centerName, String address, Integer vaccinCenterId) {
		super();
		this.id = id;
		this.centerName = centerName;
		this.address = address;
		this.vaccinCenterId = vaccinCenterId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCenterName() {
		return centerName;
	}
	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getVaccinCenterId() {
		return vaccinCenterId;
	}
	public void setVaccinCenterId(Integer vaccinCenterId) {
		this.vaccinCenterId = vaccinCenterId;
	}
	
	 
	
	
	

}
