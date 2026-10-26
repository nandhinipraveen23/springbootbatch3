package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.CitizenDto;
import com.example.demo.entity.Citizen;

@Repository
public interface CitizenRepo  extends JpaRepository<Citizen, Integer>{
	
	
	public  List<Citizen> findByVaccinCenterId(int vid);

}
