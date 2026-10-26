package com.example.demo.serviceImpl;

 

import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hamcrest.collection.ArrayAsIterableMatcher;
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

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {
	 
	/*
	 * @Mock -> create fake obj => repository obj
	 * 
	 * @InjectMocks -> create the real obj -> when( serviceimpl.insert)
	 * 
	 * when() -> define what the fake rep shud return
	 */
	
	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeServiceImpl employeeServiceImpl;
	
	
	@Test
	void testInsertEmployee()
	{
		EmployeeDto employeeDto = new EmployeeDto();
		
		employeeDto.setEmpName("Ashwini");
		employeeDto.setEmail("Ashwini123@gmail.com");
		employeeDto.setPassword("111");
		employeeDto.setRole("ADMIN");
		
Employee  employee  = new Employee ();
		
		employee.setEmpId(10);
		employee .setEmpName("Ashwini");
		employee .setEmail("Ashwini123@gmail.com");
		employee .setPassword("111");
		employee .setRole("ADMIN");
		
		
		when(employeeRepository.save(ArgumentMatchers.any(Employee.class)) )
		.thenReturn(employee);
		
		EmployeeDto result=	 employeeServiceImpl.insertEmployee(employeeDto);
		
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(10, result.getEmpId());
		Assertions.assertEquals("Ashwini", result.getEmpName());
		Assertions.assertEquals("Ashwini123@gmail.com", result.getEmail());
		Assertions.assertEquals("111", result.getPassword());
		Assertions.assertEquals("ADMIN", result.getRole());
		
	}
	
	
	
	@Test
	void testGetAllEmployee()
	{
		 
Employee  employee1  = new Employee ();
		
		employee1.setEmpId(10);
		employee1 .setEmpName("Ashwini");
		employee1 .setEmail("Ashwini123@gmail.com");
		employee1 .setPassword("111");
		employee1 .setRole("ADMIN");
		
		
Employee  employee2  = new Employee ();
		
		employee2.setEmpId(100);
		employee2 .setEmpName("nandhini");
		employee2 .setEmail("nandhini@gmail.com");
		employee2 .setPassword("1122");
		employee2 .setRole("ADMIN");
		
		when(employeeRepository.findAll())
		.thenReturn(Arrays.asList(employee1,employee2));
		
		List<EmployeeDto>  result=	 employeeServiceImpl.getAllEmployee();
		
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(2,result.size());
		
		Assertions.assertEquals(10, result.get(0).getEmpId());
		Assertions.assertEquals("Ashwini", result.get(0).getEmpName());
		Assertions.assertEquals("Ashwini123@gmail.com", result.get(0).getEmail());
		Assertions.assertEquals("111", result.get(0).getPassword());
		Assertions.assertEquals("ADMIN", result.get(0).getRole());
		
		
		Assertions.assertEquals("nandhini@gmail.com", result.get(1).getEmail());
		
	}
	
	
	
	
	
	@Test
	void testGetOneEmployee() throws EmployeeIdNotFoundException
	{
		 
 
		
		
Employee  employee2  = new Employee ();
		
		employee2.setEmpId(100);
		employee2 .setEmpName("nandhini");
		employee2 .setEmail("nandhini@gmail.com");
		employee2 .setPassword("1122");
		employee2 .setRole("ADMIN");
		
		when(employeeRepository.findById(100))
		.thenReturn(Optional.of(employee2));
		
		 EmployeeDto   result=	 employeeServiceImpl.getEmployeeById(100);
		
		
		Assertions.assertNotNull(result);
		 
		
		Assertions.assertEquals(100, result.getEmpId());
		Assertions.assertEquals("nandhini", result.getEmpName());
		Assertions.assertEquals("nandhini@gmail.com", result.getEmail());
		Assertions.assertEquals("1122", result.getPassword());
		Assertions.assertEquals("ADMIN", result.getRole());
		
	 
		
	}
	
	
	
	@Test
	void testGetOneEmployeeNotFound() throws EmployeeIdNotFoundException
	{
		 

		when(employeeRepository.findById(100))
		.thenReturn(Optional.empty());
		
		
		EmployeeIdNotFoundException exceptionObj =Assertions.assertThrows(EmployeeIdNotFoundException.class,()-> employeeServiceImpl.getEmployeeById(100));
		
		
		
		 Assertions.assertEquals("Emp ID not found", exceptionObj.getMessage());
		
	  
	 
		
	}
}
