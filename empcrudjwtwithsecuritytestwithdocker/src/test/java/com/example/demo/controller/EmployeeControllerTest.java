package com.example.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.exception.EmployeeIdNotFoundException;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.EmployeeService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
	void testFetchAllEmployees() throws Exception {

		EmployeeDto emp1 = new EmployeeDto();
		emp1.setEmpId(1);
		emp1.setEmpName("Ashwini");
		emp1.setEmail("ashwini@gmail.com");
		emp1.setRole("EMPLOYEE");
		EmployeeDto emp2 = new EmployeeDto();
		emp2.setEmpId(2);
		emp2.setEmpName("Ravi");
		emp2.setEmail("ravi@gmail.com");
		emp2.setRole("MANAGER");

		when(employeeService.getAllEmployee())
		.thenReturn(Arrays.asList(emp1, emp2));

		mockMvc.perform(get("/employees/allemp"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.length()").value(2))
		.andExpect(jsonPath("$[0].empName").value("Ashwini"))
		.andExpect(jsonPath("$[1].empName").value("Ravi"));
	}






	@Test
	void testFetchEmployeeById() throws Exception {

		EmployeeDto emp = new EmployeeDto();
		emp.setEmpId(1);
		emp.setEmpName("Ashwini");
		emp.setEmail("ashwini@gmail.com");
		emp.setRole("USER");

		when(employeeService.getEmployeeById(1))
		.thenReturn(emp);

		mockMvc.perform(get("/employees/emp/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.empId").value(1))
		.andExpect(jsonPath("$.empName").value("Ashwini"))
		.andExpect(jsonPath("$.email").value("ashwini@gmail.com"));
	}



	@Test
	void testSaveEmployee() throws Exception {

		EmployeeDto emp = new EmployeeDto();
		emp.setEmpId(3);
		emp.setEmpName("Priya");
		emp.setEmail("priya@gmail.com");
		emp.setRole("USER");

		when(employeeService.insertEmployee(
				 ArgumentMatchers.any(EmployeeDto.class)))
		.thenReturn(emp);

		String requestBody = """
				{
				    "empName": "Priya",
				    "email": "priya@gmail.com",
				    "password": "test123",
				    "role": "USER"
				}
				""";

		mockMvc.perform(post("/employees/saveemp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.empId").value(3))
		.andExpect(jsonPath("$.empName").value("Priya"))
		.andExpect(jsonPath("$.email").value("priya@gmail.com"));
	}



	@Test
	void testUpdateEmployee() throws Exception {

		EmployeeDto emp = new EmployeeDto();
		emp.setEmpId(1);
		emp.setEmpName("Ashwini Updated");
		emp.setEmail("ashwini.updated@gmail.com");
		emp.setRole("USER");

		when(employeeService.updatetEmployee(
				 ArgumentMatchers.any(EmployeeDto.class)))
		.thenReturn(emp);

		String requestBody = """
				{
				    "empId": 1,
				    "empName": "Ashwini Updated",
				    "email": "ashwini.updated@gmail.com",
				    "role": "USER"
				}
				""";

		mockMvc.perform(put("/employees/updateemp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.empId").value(1))
		.andExpect(jsonPath("$.empName").value("Ashwini Updated"))
		.andExpect(jsonPath("$.email").value("ashwini.updated@gmail.com"));
	}




	@Test
	void testPatchEmployee() throws Exception {

		EmployeeDto emp = new EmployeeDto();
		emp.setEmpId(1);
		emp.setEmpName("Ashwini Updated");
		emp.setEmail("ashwini@gmail.com");
		emp.setRole("USER");

		when(employeeService.updateEmp(
				 ArgumentMatchers.eq(1),
				 ArgumentMatchers.anyMap()))
		.thenReturn(emp);

		String requestBody = """
				{
				    "empName": "Ashwini Updated"
				}
				""";

		mockMvc.perform(patch("/employees/updateemp/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.empId").value(1))
		.andExpect(jsonPath("$.empName").value("Ashwini Updated"));
	}



	@Test
	void testDeleteEmployee() throws Exception {

		 Mockito.doNothing()
		.when(employeeService).deleteEmployee(1);

		mockMvc.perform(delete("/employees/delemp/1"))
		.andExpect(status().isOk())
		.andExpect( MockMvcResultMatchers
				.content().string("Emp Deleted"));
	}



	@Test
	void testEmployeeNotFound() throws Exception {

		when(employeeService.getEmployeeById(999))
		.thenThrow(new EmployeeIdNotFoundException("Emp ID not found"));

		mockMvc.perform(get("/employees/emp/999"))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.msg").value("Emp ID not found"));
	}



}
