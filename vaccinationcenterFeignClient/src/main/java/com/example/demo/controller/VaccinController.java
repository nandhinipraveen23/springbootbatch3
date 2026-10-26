package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CitizenDto;
import com.example.demo.dto.RequiredResponse;
import com.example.demo.dto.VaccinationDto;
import com.example.demo.service.VaccinService;

@RestController

@RequestMapping("/vaccin")
public class VaccinController {
	@Autowired
	VaccinService vaccinService;
	
	
	@PostMapping("/new")
	public ResponseEntity<VaccinationDto> saveCitizen(@RequestBody VaccinationDto vaccinationDto)
	{
		VaccinationDto res=	vaccinService.insertVaccin(vaccinationDto);
		
		return new ResponseEntity<VaccinationDto>(res,HttpStatus.CREATED);
	}
	
	@GetMapping("/vid/{vid}")
	public ResponseEntity<RequiredResponse> getInfoUsingVid(@PathVariable int vid)
	{
		RequiredResponse allres=	vaccinService.getByVaccinCenterId(vid);
		
		return new ResponseEntity<RequiredResponse>(allres,HttpStatus.OK);
	}

 

}
