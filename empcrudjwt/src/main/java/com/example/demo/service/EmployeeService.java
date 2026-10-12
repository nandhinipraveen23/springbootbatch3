package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.EmployeeLoginDto;
import com.example.demo.exception.EmployeeIdNotFoundException;
import com.example.demo.exception.InvalidUserOrPasswordExcpetion;

public interface EmployeeService {

	
	public EmployeeDto insertEmployee(EmployeeDto employeeDto);

	public EmployeeDto updatetEmployee(EmployeeDto employeeDto) throws EmployeeIdNotFoundException ;
	public EmployeeDto updateEmp(int empid, Map<String, Object> updates) throws  EmployeeIdNotFoundException, NoSuchFieldException ,IllegalArgumentException, IllegalAccessException ;
	
	public List<EmployeeDto> getAllEmployee( );
	
	public void deleteEmployee(int empid) throws EmployeeIdNotFoundException ;
	public EmployeeDto getEmployeeById(int empid)throws EmployeeIdNotFoundException;
	
	                      
	public void checkLogin(EmployeeLoginDto employeeLoginDto)throws InvalidUserOrPasswordExcpetion ;
}
