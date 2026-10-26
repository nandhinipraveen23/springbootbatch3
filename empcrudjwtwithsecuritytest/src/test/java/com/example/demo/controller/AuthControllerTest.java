
package com.example.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.EmployeeLoginDto;
import com.example.demo.exception.InvalidUserOrPasswordExcpetion;
import com.example.demo.service.EmployeeService;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Test
    void loginWithValidCredentialsShouldReturn200() throws Exception {

        EmployeeLoginDto loginDto = new EmployeeLoginDto();
        loginDto.setEmail("admin@gmail.com");
        loginDto.setRole("ADMIN");

        when(employeeService.checkLogin(
                org.mockito.ArgumentMatchers.any(EmployeeLoginDto.class)))
                .thenReturn(loginDto);

        String requestBody = """
                {
                    "email": "admin@gmail.com",
                    "password": "admin123"
                }
                """;

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").exists());
    }
    
    
    

@Test
void loginWithInvalidCredentialsShouldReturnError() throws Exception {

    when(employeeService.checkLogin(
            org.mockito.ArgumentMatchers.any(EmployeeLoginDto.class)))
            .thenThrow(new InvalidUserOrPasswordExcpetion());

    String requestBody = """
            {
                "email": "wrong@gmail.com",
                "password": "wrong123"
            }
            """;

    mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isUnauthorized());
}



@Test
void registerEmployeeShouldReturn201() throws Exception {

    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setEmpId(101);
    employeeDto.setEmpName("Ravi");
    employeeDto.setEmail("ravi@gmail.com");
    employeeDto.setPassword("test123");
    employeeDto.setRole("USER");

    when(employeeService.insertEmployee(
            org.mockito.ArgumentMatchers.any(EmployeeDto.class)))
            .thenReturn(employeeDto);

    String requestBody = """
            {
                "empId": 101,
                "empName": "Ravi",
                "email": "ravi@gmail.com",
                "password": "test123",
                "role": "USER"
            }
            """;

    mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.empName").value("Ravi"))
            .andExpect(jsonPath("$.email").value("ravi@gmail.com"));
}


}
