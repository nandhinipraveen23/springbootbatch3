package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.RequiredResponse;
import com.example.demo.dto.VaccinationDto;


public interface VaccinService {

	
	public VaccinationDto insertVaccin(VaccinationDto vaccinationDto);
	
	public RequiredResponse  getByVaccinCenterId(int id);
	 
	 
}
