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

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class VaccinServiceImpl  implements VaccinService{
	
	private final String citizen="CITIZEN-SERVICE";
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
	@CircuitBreaker(name=citizen, fallbackMethod="fallbackCitizenService")
	public RequiredResponse getByVaccinCenterId(int id) {
		
		
		 Vaccination c =	vaccinRepo.findByVaccinCenterId(id);
		 
		 
		 VaccinationDto vdto = new VaccinationDto();
		 vdto.setId(c.getId());
		 vdto.setCenterName(c.getCenterName());
		 vdto.setAddress(c.getAddress());
		 vdto.setVaccinCenterId(c.getVaccinCenterId());
		 
		 RequiredResponse Requiredresponse= new RequiredResponse();
		 
		 Requiredresponse.setVaccinationDto(vdto);
		 
		 
List<CitizenDto> listCitizen=	 restTemplate.getForObject("http://CITIZEN-SERVICE/citizen/vid/"+id, List.class);
	 
Requiredresponse.setCitizenDto(listCitizen);
		return Requiredresponse;
	}
	
	public RequiredResponse fallbackCitizenService(int id, Exception ex)
	{
		System.out.println("im getting called");
		
		 Vaccination c =	vaccinRepo.findByVaccinCenterId(id);
		 
		 
		 VaccinationDto vdto = new VaccinationDto();
		 vdto.setId(c.getId());
		 vdto.setCenterName(c.getCenterName());
		 vdto.setAddress(c.getAddress());
		 vdto.setVaccinCenterId(c.getVaccinCenterId());
		 
		 RequiredResponse requiredresponse= new RequiredResponse();
		 
		 requiredresponse.setVaccinationDto(vdto);
		 
 
		return requiredresponse;
	}

	 
}
