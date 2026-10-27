package com.example.demo.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

 
import com.example.demo.dto.CitizenDto;
import com.example.demo.entity.Citizen;
import com.example.demo.repo.CitizenRepo;
import com.example.demo.service.CitizenService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CitizenServiceImpl  implements CitizenService{
	
	
	@Autowired
	CitizenRepo citizenRepo;
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Override
	public CitizenDto insertCitizen(CitizenDto  citizenDto) {

  Citizen c = new Citizen();
  
  c.setId(citizenDto.getId());
  c.setName(citizenDto.getName());
  c.setVaccinCenterId(citizenDto.getVaccinCenterId());
  
  Citizen  cRes= citizenRepo.save(c);
		  
		   
  citizenDto.setId(cRes.getId());
		
  try {
	    String message = objectMapper.writeValueAsString(citizenDto);

	    kafkaTemplate.send("citizen-topic", message)
	        .whenComplete((result, ex) -> {
	            if (ex != null) {
	                System.err.println("Kafka send failed: " + ex.getMessage());
	            } else {
	                System.out.println("Kafka message sent: " + message);
	            }
	        });
	} catch (Exception e) {
	    e.printStackTrace();
	}
		return citizenDto;
	}

	@Override
	public List<CitizenDto> searchByVaccId(int vid) {
		 
		
	 List<Citizen> allcitizen=	citizenRepo.findByVaccinCenterId(vid);
	 
	 List<CitizenDto> allCitiDto= new ArrayList<>();
	 
	 for(Citizen c : allcitizen)
	 {
		 
		 CitizenDto cdto= new CitizenDto();
		 
		 cdto.setId(c.getId());
		 cdto.setName(c.getName());
		 cdto.setVaccinCenterId(c.getVaccinCenterId());
		 
		 allCitiDto.add(cdto);
	 }
		return allCitiDto;
	}

}
