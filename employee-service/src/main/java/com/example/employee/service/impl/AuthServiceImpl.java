package com.example.employee.service.impl;
import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.stereotype.Service; import com.example.employee.dto.*; import com.example.employee.exception.InvalidCredentialsException; import com.example.employee.entity.Employee; import com.example.employee.repository.EmployeeRepository; import com.example.employee.security.JwtService; import com.example.employee.service.AuthService;
@Service public class AuthServiceImpl implements AuthService {
 private final EmployeeRepository repo; private final PasswordEncoder encoder; private final JwtService jwtService;
 public AuthServiceImpl(EmployeeRepository repo,PasswordEncoder encoder,JwtService jwtService){this.repo=repo;this.encoder=encoder;this.jwtService=jwtService;}
 public LoginResponseDto login(LoginRequestDto r){Employee e=repo.findByEmail(r.getEmail()).orElseThrow(()->new InvalidCredentialsException("Invalid email or password"));if(!encoder.matches(r.getPassword(),e.getPassword()))throw new InvalidCredentialsException("Invalid email or password");return new LoginResponseDto("Login successful",jwtService.generateToken(e.getEmail()));}
}
