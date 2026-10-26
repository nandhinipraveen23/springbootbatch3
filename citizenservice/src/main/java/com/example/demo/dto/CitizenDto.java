package com.example.demo.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
 
public class CitizenDto {
	 
	private Integer id;
	private String name;
	private Integer vaccinCenterId;
	
	public CitizenDto() {}
	public CitizenDto(Integer id, String name, Integer vaccinCenterId) {
		super();
		this.id = id;
		this.name = name;
		this.vaccinCenterId = vaccinCenterId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getVaccinCenterId() {
		return vaccinCenterId;
	}
	public void setVaccinCenterId(Integer vaccinCenterId) {
		this.vaccinCenterId = vaccinCenterId;
	}
	
	
	

}
