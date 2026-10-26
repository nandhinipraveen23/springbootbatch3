package com.example.demo.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.CitizenDto;
import com.example.demo.dto.RequiredResponse;
import com.example.demo.dto.VaccinationDto;
import com.example.demo.entity.Vaccination;
import com.example.demo.repo.VaccinRepo;
import com.example.demo.service.VaccinService;

@Service
public class VaccinServiceImpl  implements VaccinService{
	

	
	@Autowired
	VaccinRepo vaccinRepo;
	
	@Autowired
	RestTemplate restTemplate;

	@Override
	public VaccinationDto insertVaccin(VaccinationDto  vaccinationDto) {

  Vaccination c = new Vaccination();
  
  c.setId(vaccinationDto.getId());
  c.setCenterName(vaccinationDto.getCenterName());
  c.setVaccinCenterId(vaccinationDto.getVaccinCenterId());
  
  Vaccination  cRes= vaccinRepo.save(c);
		  
		 
		 vaccinationDto.setId(cRes.getId());
		return vaccinationDto;
	}

	@Override

	public RequiredResponse getByVaccinCenterId(int id) {
		
		
		 Vaccination c =	vaccinRepo.findByVaccinCenterId(id);
		 
		 
		 VaccinationDto vdto = new VaccinationDto();
		 vdto.setId(c.getId());
		 vdto.setCenterName(c.getCenterName());
		 vdto.setAddress(c.getAddress());
		 vdto.setVaccinCenterId(c.getVaccinCenterId());
		 
		 RequiredResponse requiredresponse= new RequiredResponse();
		 
		 requiredresponse.setVaccinationDto(vdto);
		 
		 
List<CitizenDto> listCitizen=	 restTemplate.getForObject("http://CITIZEN-SERVICE/citizen/vid/"+id, List.class);
	 
requiredresponse.setCitizenDto(listCitizen);
		return requiredresponse;
	}

	
	
	
	
	 
}
