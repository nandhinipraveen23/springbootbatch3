package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.ResponseMessage;
import com.example.demo.exception.EmployeeIdNotFoundException;
import com.example.demo.service.EmployeeService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	@PostMapping("/saveemp")
	public  ResponseEntity<EmployeeDto> saveEmp(@RequestBody EmployeeDto employeeDto)
	{
		EmployeeDto empDto=	employeeService.insertEmployee(employeeDto);
		
		return new ResponseEntity<EmployeeDto>(empDto, HttpStatus.CREATED);
		
	}
	
	
	@PutMapping("/updateemp")
	public  ResponseEntity<EmployeeDto> editEmp(@RequestBody EmployeeDto employeeDto) throws EmployeeIdNotFoundException
	{
		
	 
		EmployeeDto empDto=	employeeService.updatetEmployee(employeeDto);
		
		return new ResponseEntity<EmployeeDto>(empDto, HttpStatus.CREATED);
	 
		
		
		
		
	 
	}
	
	@PatchMapping ("/updateemp/{empid}")
	public  ResponseEntity<EmployeeDto> patchEmp(@PathVariable int empid ,@RequestBody  Map<String, Object> updates) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, EmployeeIdNotFoundException
	{
		
	 
		EmployeeDto empDto=	employeeService.updateEmp(empid, updates);
		
		return new ResponseEntity<EmployeeDto>(empDto, HttpStatus.CREATED);
		
		
		
	  
	  
	}
	
	
	
	
	@GetMapping("/allemp")
	public ResponseEntity<List<EmployeeDto>> fetchAllEmp()
	{
		List <EmployeeDto> allEmpDto=	employeeService.getAllEmployee();
		
		return new ResponseEntity<List<EmployeeDto>>(allEmpDto, HttpStatus.OK);
		
	}
	
	@GetMapping("/emp/{id}")
	public ResponseEntity<EmployeeDto> fetchEmp(@PathVariable("id") int empid) throws EmployeeIdNotFoundException
	{
	  
		 EmployeeDto  empDto=	employeeService.getEmployeeById(empid);
		
		return new ResponseEntity< EmployeeDto >(empDto, HttpStatus.OK);
		 
		
		 
		
	}
	@DeleteMapping("/delemp/{empid}")
	public ResponseEntity<String> deleteEmp(@PathVariable int empid) throws EmployeeIdNotFoundException
	{
	 
		 
		 	employeeService.deleteEmployee(empid);
		
		return new ResponseEntity<String>("Emp Deleted" , HttpStatus.OK);
		 
		
	 
	}
	
	
	
	
}
