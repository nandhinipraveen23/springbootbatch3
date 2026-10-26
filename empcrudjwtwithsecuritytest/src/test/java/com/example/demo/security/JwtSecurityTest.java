package com.example.demo.security;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.service.EmployeeService;

@SpringBootTest
@AutoConfigureMockMvc
class JwtSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @MockitoBean
    private EmployeeService employeeService;

    @Test
    void requestWithoutTokenShouldReturn401() throws Exception {
        mockMvc.perform(get("/employees/allemp"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void requestWithValidTokenShouldReturn200() throws Exception {
        String token = jwtUtil.generateToken(
                "admin@gmail.com", "ADMIN");

        when(employeeService.getAllEmployee())
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/employees/allemp")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void requestWithInvalidTokenShouldReturn401() throws Exception {
        mockMvc.perform(get("/employees/allemp")
                .header("Authorization", "Bearer invalid.jwt.token"))
                .andExpect(status().isUnauthorized());
    }
    
    
    @Test
    void requestWithUserTokenCannotSaveEmployee() throws Exception {

        String token = jwtUtil.generateToken("user@gmail.com", "USER");

        String requestBody = """
            {
                "empName": "Test User",
                "email": "testuser@gmail.com",
                "password": "test123",
                "role": "USER"
            }
            """;

        mockMvc.perform(post("/employees/saveemp")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isForbidden());
    }
    
    
    
    @Test
    void requestWithUserTokenCannotUpdateEmployee() throws Exception {

        String token = jwtUtil.generateToken(
                "user@gmail.com", "USER");

        String requestBody = """
            {
                "empId": 1,
                "empName": "Updated Employee",
                "email": "updated@gmail.com",
                "role": "USER"
            }
            """;

        mockMvc.perform(put("/employees/updateemp")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isForbidden());
    }
    
    
    

@Test
void requestWithManagerTokenCanUpdateEmployee() throws Exception {

    String token = jwtUtil.generateToken(
            "manager@gmail.com", "MANAGER");

    String requestBody = """
        {
            "empId": 1,
            "empName": "Updated Employee",
            "email": "updated@gmail.com",
            "role": "USER"
        }
        """;

    EmployeeDto updatedEmployee = new EmployeeDto();
    updatedEmployee.setEmpId(1);
    updatedEmployee.setEmpName("Updated Employee");
    updatedEmployee.setEmail("updated@gmail.com");
    updatedEmployee.setRole("USER");

    when(employeeService.updatetEmployee(
            org.mockito.ArgumentMatchers.any(EmployeeDto.class)))
            .thenReturn(updatedEmployee);

    mockMvc.perform(put("/employees/updateemp")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.empName").value("Updated Employee"));
}


@Test
void requestWithManagerTokenCanDeleteEmployee() throws Exception {

    String token = jwtUtil.generateToken(
        "manager@gmail.com", "MANAGER"
    );

    org.mockito.Mockito.doNothing()
        .when(employeeService).deleteEmployee(1);

    mockMvc.perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                .delete("/employees/delemp/1")
                .header("Authorization", "Bearer " + token)
        )
        .andExpect(status().isOk())
        .andExpect(
            org.springframework.test.web.servlet.result.MockMvcResultMatchers
                .content().string("Emp Deleted")
        );
}


}