package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.EmployeeLoginDto;
import com.example.demo.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

	// select * from emp where email="ashwini"gmial.com" and pass="123"
	
	@Query("select e from Employee e where email=:empEmail and password=:empPassword")
	public  Optional<Employee> login(String empEmail, String empPassword);
}
