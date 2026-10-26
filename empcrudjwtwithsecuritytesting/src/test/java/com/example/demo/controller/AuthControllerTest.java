package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.EmployeeLoginDto;
import com.example.demo.dto.ResponseMessage;
import com.example.demo.exception.InvalidUserOrPasswordExcpetion;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.EmployeeService;

@RestController
@RequestMapping("/auth")
public class AuthControllerTest {
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	JwtUtil jwtUtil;
	@PostMapping("/login")
	public ResponseEntity<?> empLogin(@RequestBody EmployeeLoginDto employeeLoginDto) throws InvalidUserOrPasswordExcpetion
	{
		
		 
		 
		EmployeeLoginDto eLog=	employeeService.checkLogin(employeeLoginDto);
		
		 
	 
			return ResponseEntity.ok(new ResponseMessage( jwtUtil.generateToken(eLog.getEmail(),eLog.getRole())));
		 
		 
		 
	}
	
	@PostMapping("/register")
	public  ResponseEntity<EmployeeDto> saveEmp(@RequestBody EmployeeDto employeeDto)
	{
		EmployeeDto empDto=	employeeService.insertEmployee(employeeDto);
		
		return new ResponseEntity<EmployeeDto>(empDto, HttpStatus.CREATED);
		
	}

}
