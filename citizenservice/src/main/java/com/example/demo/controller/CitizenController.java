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
import com.example.demo.service.CitizenService;

@RestController

@RequestMapping("/citizen")
public class CitizenController {
	@Autowired
	CitizenService citizenService;
	
	
	@PostMapping("/new")
	public ResponseEntity<CitizenDto> saveCitizen(@RequestBody CitizenDto citizenDto)
	{
		CitizenDto res=	citizenService.insertCitizen(citizenDto);
		
		return new ResponseEntity<CitizenDto>(res,HttpStatus.CREATED);
	}
	
	

	@GetMapping("/vid/{vid}")
	public ResponseEntity<List<CitizenDto>> allCitizen(@PathVariable int vid)
	{
		List<CitizenDto> allres=	citizenService.searchByVaccId(vid);
		
		return new ResponseEntity<List<CitizenDto>>(allres,HttpStatus.OK);
	}

}
