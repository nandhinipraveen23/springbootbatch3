package com.example.employee.controller;
import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*; import jakarta.validation.Valid; import com.example.employee.dto.*; import com.example.employee.service.AuthService;
@RestController @RequestMapping("/auth") 
public class AuthController
{
	private final AuthService authService; 
	public AuthController(AuthService authService)
	{
		this.authService=authService;
	}
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request)
	{
		return ResponseEntity.ok(authService.login(request));
		} }
