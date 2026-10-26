package com.example.demo.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 
import com.example.demo.dto.CitizenDto;
import com.example.demo.entity.Citizen;
import com.example.demo.repo.CitizenRepo;
import com.example.demo.service.CitizenService;

@Service
public class CitizenServiceImpl  implements CitizenService{
	
	
	@Autowired
	CitizenRepo citizenRepo;

	@Override
	public CitizenDto insertCitizen(CitizenDto  citizenDto) {

  Citizen c = new Citizen();
  
  c.setId(citizenDto.getId());
  c.setName(citizenDto.getName());
  c.setVaccinCenterId(citizenDto.getVaccinCenterId());
  
  Citizen  cRes= citizenRepo.save(c);
		  
		   
  citizenDto.setId(cRes.getId());
		
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
