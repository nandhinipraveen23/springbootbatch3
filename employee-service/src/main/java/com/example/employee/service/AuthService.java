package com.example.employee.service;
import com.example.employee.dto.LoginRequestDto; import com.example.employee.dto.LoginResponseDto;
public interface AuthService { LoginResponseDto login(LoginRequestDto request); }
