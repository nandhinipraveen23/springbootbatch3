package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EmployeeLoginDto;
import com.example.demo.service.EmployeeService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	EmployeeService employeeService;
	
	@PostMapping("/login")
	public ResponseEntity<String> empLogin(@RequestBody EmployeeLoginDto employeeLoginDto)
	{
		
		System.out.println("inside the controller "+ employeeLoginDto.getEmail()+" "+ employeeLoginDto.getPassword());
		
		try {
			employeeService.checkLogin(employeeLoginDto);
			return new ResponseEntity<String>("Login Success",HttpStatus.OK);
		} catch (Exception e) {
			 
			 return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
