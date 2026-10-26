package com.example.demo.serviceImpl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.EmployeeLoginDto;
import com.example.demo.entity.Employee;
import com.example.demo.exception.EmployeeIdNotFoundException;
import com.example.demo.exception.InvalidUserOrPasswordExcpetion;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest   {
	 
 
	@Mock
	private EmployeeRepository repository;
	
	@InjectMocks
	private EmployeeServiceImpl employeeServiceImpl;
	
	@Test
	void testInsertEmployee()
	{
		// create logindto wrong set email, password
		//when(repo.login(wrnoguname and wrongpass) // return Optional.empty()
		EmployeeDto employeeDto = new EmployeeDto();
		
		employeeDto.setEmpName("nandhini");
		employeeDto.setEmail("np@gmail.com");
		employeeDto.setPassword("123");
		employeeDto.setRole("ADMIN");
		
		
		
Employee  employee  = new Employee  ();
		employee.setEmpId(100);
		employee .setEmpName("nandhini");
		employee .setEmail("np@gmail.com");
		employee .setPassword("123");
		employee .setRole("ADMIN");
		
		when(repository.save(any(Employee.class)))
		.thenReturn(employee);
		
		
	EmployeeDto eDto=	employeeServiceImpl.insertEmployee(employeeDto);
	
	 assertNotNull(eDto);
	 assertEquals(100, eDto.getEmpId());
	 assertEquals("nandhini", eDto.getEmpName());
	 assertEquals("np@gmail.com", eDto.getEmail());
	 assertEquals("123", eDto.getPassword());
	 assertEquals("ADMIN", eDto.getRole());
	 
		
	}
	
	
	
	@Test
	void testAllEmployee()
	{
		Employee  e1 = new Employee ();
		
		e1.setEmpId(100);
		e1.setEmpName("pravin");
		e1.setEmail("pravin@gmail.com");
		e1.setPassword("123");
		e1.setRole("ADMIN");
		
		
Employee  e2 = new Employee ();
		e2.setEmpId(20);
		e2.setEmpName("ganga");
		e2.setEmail("ganga@gmail.com");
		e2.setPassword("123");
		e2.setRole("ADMIN");
		

		
		when(repository.findAll())
		.thenReturn(Arrays.asList(e1,e2));
		
		
		List<EmployeeDto> allEmpDto =	employeeServiceImpl.getAllEmployee();
	
	 assertNotNull(allEmpDto);
	 assertEquals(20,allEmpDto.get(1).getEmpId());
	 assertEquals("ganga", allEmpDto.get(1).getEmpName());
	 assertEquals("ganga@gmail.com", allEmpDto.get(1).getEmail());
	 assertEquals("123", allEmpDto.get(1).getPassword());
	 assertEquals("ADMIN", allEmpDto.get(1).getRole());
	 
	 assertEquals("pravin@gmail.com", allEmpDto.get(0).getEmail());
	 
		
	}

	
	
	

	@Test
	void testOneEmployee() throws EmployeeIdNotFoundException
	{
		Employee  e1 = new Employee ();
		
		e1.setEmpId(100);
		e1.setEmpName("pravin");
		e1.setEmail("pravin@gmail.com");
		e1.setPassword("123");
		e1.setRole("ADMIN");
		
		
 

		
		when(repository.findById(100))
		.thenReturn(Optional.of(e1));
		
		
		 EmployeeDto  empDto =	employeeServiceImpl.getEmployeeById(100);
	
	 assertNotNull(empDto);
	 assertEquals(100,empDto.getEmpId());
	 assertEquals("pravin",empDto.getEmpName());
	 assertEquals("pravin@gmail.com", empDto.getEmail());
	 assertEquals("123", empDto.getPassword());
	 assertEquals("ADMIN", empDto.getRole());
	 
	 
		
	}
	
	@Test
	void testOneEmployeeNotFound()  
	{
		 	
		when(repository.findById(100))
		.thenReturn(Optional.empty());
		
		
		EmployeeIdNotFoundException exception = assertThrows(EmployeeIdNotFoundException.class,
				()->employeeServiceImpl.getEmployeeById(100)
				
				 );
				
	 assertEquals("Emp ID not found",exception.getMessage());
 
	 
	 
		
	}
}
