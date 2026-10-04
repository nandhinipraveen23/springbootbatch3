package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.dto.EmployeeDto;

public interface EmployeeService {

	
	public EmployeeDto insertEmployee(EmployeeDto employeeDto);

	public EmployeeDto updatetEmployee(EmployeeDto employeeDto) throws Exception ;
	public EmployeeDto updateEmp(int empid, Map<String, Object> updates) throws Exception ;
	
	public List<EmployeeDto> getAllEmployee( );
	
	public void deleteEmployee(int empid) throws Exception ;
	public EmployeeDto getEmployeeById(int empid)throws Exception;
}
