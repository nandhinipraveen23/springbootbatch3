 package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

 
import com.example.demo.entity.Vaccination;

@Repository
public interface VaccinRepo  extends JpaRepository<Vaccination, Integer>{
	
 
	public Vaccination findByVaccinCenterId(int vid);

}
