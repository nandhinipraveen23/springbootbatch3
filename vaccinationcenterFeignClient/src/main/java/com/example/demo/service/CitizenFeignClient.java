package com.example.demo.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.CitizenDto;


@FeignClient(name="CITIZEN-SERVICE")
public interface CitizenFeignClient {

	@GetMapping("/citizen/vid/{vid}")
	public List<CitizenDto> getCitizenInfo(@PathVariable int id );
	 
}
