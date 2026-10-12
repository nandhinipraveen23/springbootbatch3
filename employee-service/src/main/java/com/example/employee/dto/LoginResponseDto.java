package com.example.employee.dto;
public class LoginResponseDto { private String message; private String token; private String tokenType="Bearer";
 public LoginResponseDto(){} public LoginResponseDto(String message,String token){this.message=message;this.token=token;}
 public String getMessage(){return message;} public void setMessage(String v){message=v;} public String getToken(){return token;} public void setToken(String v){token=v;} public String getTokenType(){return tokenType;} public void setTokenType(String v){tokenType=v;} }
