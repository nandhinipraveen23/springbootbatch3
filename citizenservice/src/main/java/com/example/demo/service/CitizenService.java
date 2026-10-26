package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.CitizenDto;


public interface CitizenService {

	
	public CitizenDto insertCitizen(CitizenDto ctCitizenDto);
	public  List<CitizenDto> searchByVaccId(int vid);
	 
}
