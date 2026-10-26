package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.ResponseMessage;
import com.example.demo.entity.Employee;
import com.example.demo.exception.EmployeeIdNotFoundException;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.EmployeeService;

import jakarta.websocket.server.PathParam;


@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class EmployeeControllerTest {
	
 
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private EmployeeService employeeService;
	
	@MockitoBean
	private JwtUtil jwtUtil;
	
	@Test
	void testSaveEmployee() throws Exception
	{
EmployeeDto employeeDto = new EmployeeDto();
		
		 
		employeeDto.setEmpName("nandhini");
		employeeDto.setEmail("np@gmail.com");
		employeeDto.setPassword("123");
		employeeDto.setRole("USER");
		
		
		when(employeeService.insertEmployee(any(EmployeeDto.class)))
		.thenReturn(employeeDto);
		
		
		String requestBody="""
				{
				 
				"empName":"nandhini",
				"email":"np@gmail.com",
				"password":"123",
				"role":"USER"
				
				}
				
				""";
		
		mockMvc.perform(post("/employees/saveemp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isCreated())
				 
				.andExpect(jsonPath("$.empName").value("nandhini"))
				.andExpect(jsonPath("$.email").value("np@gmail.com"))
				.andExpect(jsonPath("$.password").value("123"))
				.andExpect(jsonPath("$.role").value("USER"));
						
	}
	
	
	@Test
	void testUpdateEmployee() throws Exception
	{
EmployeeDto employeeDto = new EmployeeDto();
		
		 employeeDto.setEmpId(10);
		employeeDto.setEmpName("nandhiniupdated");
		employeeDto.setEmail("npupdated@gmail.com");
		employeeDto.setPassword("123updated");
		employeeDto.setRole("ADMIN");
		
		
		when(employeeService.updatetEmployee(any(EmployeeDto.class)))
		.thenReturn(employeeDto);
		
		
		String requestBody="""
				{
				 "empId":10,
				"empName":"nandhiniupdated",
				"email":"npupdated@gmail.com",
				"password":"123updated",
				"role":"ADMIN"
				
				}
				
				""";
		
		mockMvc.perform(put("/employees/updateemp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.empId").value(10))
				.andExpect(jsonPath("$.empName").value("nandhiniupdated"))
				.andExpect(jsonPath("$.email").value("npupdated@gmail.com"))
				.andExpect(jsonPath("$.password").value("123updated"))
				.andExpect(jsonPath("$.role").value("ADMIN"));
						
	}
	
	
	
	@Test
	void testFetchEmployeeById() throws Exception
	{
EmployeeDto employeeDto = new EmployeeDto();
		
		 employeeDto.setEmpId(10);
		employeeDto.setEmpName("nandhiniupdated");
		employeeDto.setEmail("npupdated@gmail.com");
		employeeDto.setPassword("123updated");
		employeeDto.setRole("ADMIN");
		
		
		when(employeeService.getEmployeeById(10))
		.thenReturn(employeeDto);
		
	 
		
		mockMvc.perform(get("/employees/emp/10"))
				 
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.empId").value(10))
				.andExpect(jsonPath("$.empName").value("nandhiniupdated"))
				.andExpect(jsonPath("$.email").value("npupdated@gmail.com"))
				.andExpect(jsonPath("$.password").value("123updated"))
				.andExpect(jsonPath("$.role").value("ADMIN"));
						
	}
}
