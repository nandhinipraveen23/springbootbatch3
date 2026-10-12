package com.example.employee.service;
import java.util.List; import com.example.employee.dto.EmployeeRequestDto; import com.example.employee.dto.EmployeeResponseDto;
public interface EmployeeService { EmployeeResponseDto create(EmployeeRequestDto request); List<EmployeeResponseDto> findAll(); EmployeeResponseDto findById(Integer id); EmployeeResponseDto update(Integer id,EmployeeRequestDto request); void delete(Integer id); }
