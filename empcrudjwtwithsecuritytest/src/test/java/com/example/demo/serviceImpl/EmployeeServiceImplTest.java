
package com.example.demo.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.EmployeeLoginDto;
import com.example.demo.entity.Employee;
import com.example.demo.exception.EmployeeIdNotFoundException;
import com.example.demo.exception.InvalidUserOrPasswordExcpetion;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.serviceImpl.EmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void testInsertEmployee() {

        // Arrange: Prepare input data
        EmployeeDto dto = new EmployeeDto();
        dto.setEmpName("Ashwini");
        dto.setEmail("ashwini@gmail.com");
        dto.setPassword("1234");
        dto.setRole("EMPLOYEE");

        // Arrange: Mock repository response
        Employee savedEmployee = new Employee();
        savedEmployee.setEmpId(2);
        savedEmployee.setEmpName("Ashwini");
        savedEmployee.setEmail("ashwini@gmail.com");
        savedEmployee.setPassword("1234");
        savedEmployee.setRole("EMPLOYEE");

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(savedEmployee);

        // Act: Call the service method
        EmployeeDto result = employeeService.insertEmployee(dto);

        // Assert: Verify the output
        assertNotNull(result);
        assertEquals(2, result.getEmpId());
        assertEquals("Ashwini", result.getEmpName());
        assertEquals("ashwini@gmail.com", result.getEmail());
        assertEquals("EMPLOYEE", result.getRole());

        // Verify repository was called once
        verify(employeeRepository, times(1))
                .save(any(Employee.class));
        
        
        
        
    }
    

@Test
void testCheckLoginWithValidCredentials() throws Exception {

    // Arrange: Login input
    EmployeeLoginDto loginDto = new EmployeeLoginDto();
    loginDto.setEmail("ashwini@gmail.com");
    loginDto.setPassword("1234");

    // Arrange: Employee returned by repository
    Employee employee = new Employee();
    employee.setEmpId(2);
    employee.setEmpName("Ashwini");
    employee.setEmail("ashwini@gmail.com");
    employee.setPassword("1234");
    employee.setRole("EMPLOYEE");

    when(employeeRepository.login(
            "ashwini@gmail.com", "1234"))
            .thenReturn(Optional.of(employee));

    // Act: Call service method
    EmployeeLoginDto result =
            employeeService.checkLogin(loginDto);

    // Assert: Verify returned data
    assertNotNull(result);
    assertEquals("ashwini@gmail.com", result.getEmail());
    assertEquals("EMPLOYEE", result.getRole());

    // Verify repository invocation
    verify(employeeRepository, times(1))
            .login("ashwini@gmail.com", "1234");
}



@Test
void testCheckLoginWithInvalidCredentials() throws Exception {

    // Arrange
    EmployeeLoginDto loginDto = new EmployeeLoginDto();
    loginDto.setEmail("wrong@gmail.com");
    loginDto.setPassword("wrong123");

    // Simulate employee not found
    when(employeeRepository.login(
            "wrong@gmail.com", "wrong123"))
            .thenReturn(Optional.empty());

    // Act and Assert
    InvalidUserOrPasswordExcpetion exception =
            assertThrows(
                    InvalidUserOrPasswordExcpetion.class,
                    () -> employeeService.checkLogin(loginDto)
            );

    assertEquals(
            "Invalid email or password",
            exception.getMessage()
    );

    // Verify repository invocation
    verify(employeeRepository, times(1))
            .login("wrong@gmail.com", "wrong123");
}



@Test
void testGetEmployeeByIdWhenEmployeeDoesNotExist() {

    // Arrange
    when(employeeRepository.findById(99))
            .thenReturn(Optional.empty());

    // Act and Assert
    assertThrows(
            EmployeeIdNotFoundException.class,
            () -> employeeService.getEmployeeById(99)
    );

    verify(employeeRepository, times(1)).findById(99);
}



@Test
void testGetAllEmployee() {
    Employee emp1 = new Employee();
    emp1.setEmpId(1);
    emp1.setEmpName("Ashwini");
    emp1.setEmail("ashwini@gmail.com");
    emp1.setPassword("1234");
    emp1.setRole("EMPLOYEE");

    Employee emp2 = new Employee();
    emp2.setEmpId(2);
    emp2.setEmpName("Ravi");
    emp2.setEmail("ravi@gmail.com");
    emp2.setPassword("5678");
    emp2.setRole("MANAGER");

    when(employeeRepository.findAll())
            .thenReturn(Arrays.asList(emp1, emp2));

    List<EmployeeDto> result = employeeService.getAllEmployee();

    assertNotNull(result);
    assertEquals(2, result.size());

    assertEquals("Ashwini", result.get(0).getEmpName());
    assertEquals("Ravi", result.get(1).getEmpName());

    assertEquals("EMPLOYEE", result.get(0).getRole());
    assertEquals("MANAGER", result.get(1).getRole());

    verify(employeeRepository, times(1)).findAll();
}


@Test
void testDeleteEmployee() throws EmployeeIdNotFoundException {
    Employee employee = new Employee();
    employee.setEmpId(2);
    employee.setEmpName("Ashwini");
    employee.setEmail("ashwini@gmail.com");
    employee.setPassword("1234");
    employee.setRole("EMPLOYEE");

    when(employeeRepository.findById(2))
            .thenReturn(Optional.of(employee));

    employeeService.deleteEmployee(2);

    verify(employeeRepository, times(1)).findById(2);
    verify(employeeRepository, times(1)).delete(employee);
}

@Test
void testDeleteEmployeeWhenIdNotFound() throws Exception {

    when(employeeRepository.findById(99))
            .thenReturn(Optional.empty());

    EmployeeIdNotFoundException exception =
            assertThrows(
                    EmployeeIdNotFoundException.class,
                    () -> employeeService.deleteEmployee(99)
            );

    assertEquals("Emp ID not found", exception.getMessage());

    verify(employeeRepository, times(1)).findById(99);

    verify(employeeRepository, never())
            .delete(any(Employee.class));
}


@Test
void testUpdateEmployee() throws Exception {

    Employee existingEmployee = new Employee();
    existingEmployee.setEmpId(2);
    existingEmployee.setEmpName("Ashwini");
    existingEmployee.setEmail("ashwini@gmail.com");
    existingEmployee.setPassword("1234");
    existingEmployee.setRole("EMPLOYEE");

    EmployeeDto updateDto = new EmployeeDto();
    updateDto.setEmpId(2);
    updateDto.setEmpName("Ashwini Updated");
    updateDto.setEmail("ashwini.updated@gmail.com");
    updateDto.setPassword("5678");

    when(employeeRepository.findById(2))
            .thenReturn(Optional.of(existingEmployee));

    when(employeeRepository.save(any(Employee.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

    EmployeeDto result =
            employeeService.updatetEmployee(updateDto);

    assertNotNull(result);
    assertEquals(2, result.getEmpId());
    assertEquals("Ashwini Updated", result.getEmpName());
    assertEquals("ashwini.updated@gmail.com", result.getEmail());

    verify(employeeRepository, times(1)).findById(2);
    verify(employeeRepository, times(1)).save(any(Employee.class));
}


@Test
void testUpdateEmployeeWhenIdNotFound() throws Exception {

    EmployeeDto updateDto = new EmployeeDto();
    updateDto.setEmpId(99);
    updateDto.setEmpName("New Employee");
    updateDto.setEmail("new@gmail.com");

    when(employeeRepository.findById(99))
            .thenReturn(Optional.empty());

    EmployeeIdNotFoundException exception =
            assertThrows(
                    EmployeeIdNotFoundException.class,
                    () -> employeeService.updatetEmployee(updateDto)
            );

    assertEquals("Emp ID not found", exception.getMessage());

    verify(employeeRepository, times(1)).findById(99);

    verify(employeeRepository, never())
            .save(any(Employee.class));
}



@Test
void testUpdateEmpPartial() throws Exception {

    Employee employee = new Employee();
    employee.setEmpId(2);
    employee.setEmpName("Ashwini");
    employee.setEmail("ashwini@gmail.com");
    employee.setPassword("1234");
    employee.setRole("EMPLOYEE");

    Map<String, Object> updates = new HashMap<>();
    updates.put("empName", "Ashwini Updated");

    when(employeeRepository.findById(2))
            .thenReturn(Optional.of(employee));

    when(employeeRepository.save(any(Employee.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

    EmployeeDto result = employeeService.updateEmp(2, updates);

    assertNotNull(result);
    assertEquals(2, result.getEmpId());
    assertEquals("Ashwini Updated", result.getEmpName());
    assertEquals("ashwini@gmail.com", result.getEmail());

    verify(employeeRepository, times(1)).findById(2);
    verify(employeeRepository, times(1)).save(any(Employee.class));
}




@Test
void testUpdateEmpWhenIdNotFound() throws Exception {

    Map<String, Object> updates = new HashMap<>();
    updates.put("empName", "New Employee");

    when(employeeRepository.findById(99))
            .thenReturn(Optional.empty());

    EmployeeIdNotFoundException exception =
            assertThrows(
                    EmployeeIdNotFoundException.class,
                    () -> employeeService.updateEmp(99, updates)
            );

    assertEquals("Emp ID not found", exception.getMessage());

    verify(employeeRepository, times(1)).findById(99);

    verify(employeeRepository, never())
            .save(any(Employee.class));
}
}
