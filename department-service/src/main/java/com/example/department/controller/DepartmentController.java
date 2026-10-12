package com.example.department.controller;

import com.example.department.dto.DepartmentRequestDto;
import com.example.department.dto.DepartmentResponseDto;
import com.example.department.service.DepartmentService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponseDto create(
            @RequestBody DepartmentRequestDto request) {
        return departmentService.create(request);
    }

    @GetMapping
    public List<DepartmentResponseDto> getAll() {
        return departmentService.getAll();
    }

    @GetMapping("/{id}")
    public DepartmentResponseDto getById(
            @PathVariable("id") Long id) {
        return departmentService.get(id);
    }

    @PutMapping("/{id}")
    public DepartmentResponseDto update(
            @PathVariable("id") Long id,
            @RequestBody DepartmentRequestDto request) {
        return departmentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("id") Long id) {
        departmentService.delete(id);
    }
}