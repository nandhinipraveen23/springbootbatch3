package com.example.demo.controller;

 
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.entity.Employee;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.EmployeeService;

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
void testFetchAllEmp() throws Exception
{
	
	EmployeeDto  employee1  = new EmployeeDto ();
	
	employee1.setEmpId(10);
	employee1 .setEmpName("Ashwini");
	employee1 .setEmail("Ashwini123@gmail.com");
	employee1 .setPassword("111");
	employee1 .setRole("ADMIN");
	
	
	EmployeeDto  employee2  = new EmployeeDto ();
	
	employee2.setEmpId(100);
	employee2 .setEmpName("nandhini");
	employee2 .setEmail("nandhini@gmail.com");
	employee2 .setPassword("1122");
	employee2 .setRole("ADMIN");
	
	when(employeeService.getAllEmployee())
	.thenReturn(Arrays.asList(employee1,employee2));
	
	
	mockMvc.perform(get("/employees/allemp"))
	.andExpect(status().isOk())
	.andExpect(jsonPath("$.length()").value(2))
	.andExpect(jsonPath("$[0].empName").value("Ashwini"))
	.andExpect(jsonPath("$[0].email").value("Ashwini123@gmail.com"))
	.andExpect(jsonPath("$[0].password").value("111"))
	.andExpect(jsonPath("$[0].role").value("ADMIN"))
	
	.andExpect(jsonPath("$[1].email").value("nandhini@gmail.com"));
	
}



@Test
void testFetchOneEmp() throws Exception
{
	
	EmployeeDto  employee1  = new EmployeeDto ();
	
	employee1.setEmpId(10);
	employee1 .setEmpName("Ashwini");
	employee1 .setEmail("Ashwini123@gmail.com");
	employee1 .setPassword("111");
	employee1 .setRole("ADMIN");
	
	
	 
	
	when(employeeService.getEmployeeById(10))
	.thenReturn( employee1 );
	
	
	mockMvc.perform(get("/employees/emp/10"))
	.andExpect(status().isOk())
	 
	.andExpect(jsonPath("$.empName").value("Ashwini"))
	.andExpect(jsonPath("$.email").value("Ashwini123@gmail.com"))
	.andExpect(jsonPath("$.password").value("111"))
	.andExpect(jsonPath("$.role").value("ADMIN"));
 
}

@Test
void testSaveEmp() throws Exception
{
	
	EmployeeDto  employee1  = new EmployeeDto ();
	
	employee1.setEmpId(10);
	employee1 .setEmpName("nandhini");
	employee1 .setEmail("np@gmail.com");
	employee1 .setPassword("111");
	employee1 .setRole("ADMIN");
	
	
	 
	
	when(employeeService.insertEmployee(any(EmployeeDto.class)))
	.thenReturn(employee1);
	
	
	
	String requestBody="""
			{
			
			"empName":"nandhini",
			"email":"np@gmail.com",
			"password":"123",
			"role":"ADMIN"
			}
			
			""";
	mockMvc.perform(post("/employees/saveemp")
			.contentType(MediaType.APPLICATION_JSON)
			.content(requestBody))
	
	
	
	.andExpect(status().isCreated())
	.andExpect(jsonPath("$.empId").value(10))
	.andExpect(jsonPath("$.empName").value("nandhini"))
	.andExpect(jsonPath("$.email").value("np@gmail.com"))
	.andExpect(jsonPath("$.password").value("111"))
	.andExpect(jsonPath("$.role").value("ADMIN"));
	
	 
	
}
}
