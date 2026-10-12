package com.example.department.service;
import java.util.List; import com.example.department.dto.*;
public interface DepartmentService { DepartmentResponseDto create(DepartmentRequestDto r); List<DepartmentResponseDto> findAll(); DepartmentResponseDto findById(Long id); DepartmentResponseDto update(Long id,DepartmentRequestDto r); void delete(Long id); }
