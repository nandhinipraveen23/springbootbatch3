package com.example.demo.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.CitizenDto;
import com.example.demo.dto.RequiredResponse;
import com.example.demo.dto.VaccinationDto;
import com.example.demo.entity.Vaccination;
import com.example.demo.repo.VaccinRepo;
import com.example.demo.service.VaccinService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class VaccinServiceImpl  implements VaccinService{
	

	
	@Autowired
	VaccinRepo vaccinRepo;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

//	@Override
//	public VaccinationDto insertVaccin(VaccinationDto  vaccinationDto) {
//
//  Vaccination c = new Vaccination();
//  
//  c.setId(vaccinationDto.getId());
//  c.setCenterName(vaccinationDto.getCenterName());
//  c.setVaccinCenterId(vaccinationDto.getVaccinCenterId());
//  
//  Vaccination  cRes= vaccinRepo.save(c);
//		  
//		 
//		 vaccinationDto.setId(cRes.getId());
//		 
//		 try {
//			    String message = objectMapper.writeValueAsString(vaccinationDto);
//
//			    kafkaTemplate.send("vaccination-topic", message)
//			        .whenComplete((result, ex) -> {
//			            if (ex != null) {
//			                System.err.println("Kafka send failed: " + ex.getMessage());
//			            } else {
//			                System.out.println("Kafka message sent: " + message);
//			            }
//			        });
//			} catch (Exception e) {
//			    e.printStackTrace();
//			}
//		return vaccinationDto;
//	}
	
	@Override
	public VaccinationDto insertVaccin(VaccinationDto vaccinationDto) {

	    System.out.println("STEP 1: Request received");

	    Vaccination c = new Vaccination();
	    c.setId(vaccinationDto.getId());
	    c.setCenterName(vaccinationDto.getCenterName());
	    c.setAddress(vaccinationDto.getAddress());
	    c.setVaccinCenterId(vaccinationDto.getVaccinCenterId());

	    System.out.println("STEP 2: Saving to database");

	    Vaccination cRes = vaccinRepo.save(c);
	    vaccinationDto.setId(cRes.getId());

	    System.out.println("STEP 3: Database save successful");

	    try {
	        String message = objectMapper.writeValueAsString(vaccinationDto);

	        System.out.println("STEP 4: JSON created: " + message);

	        kafkaTemplate.send("vaccination-topic", message)
	            .whenComplete((result, ex) -> {
	                if (ex != null) {
	                    System.err.println("Kafka send failed: " + ex.getMessage());
	                } else {
	                    System.out.println("STEP 5: Kafka message sent");
	                }
	            });

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

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
