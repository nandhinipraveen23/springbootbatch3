package com.example.notification.dto;
import jakarta.validation.constraints.Email; import jakarta.validation.constraints.NotBlank;
public class NotificationRequestDto { @NotBlank @Email private String recipient; @NotBlank private String message; private String type; public String getRecipient(){return recipient;} public void setRecipient(String v){recipient=v;} public String getMessage(){return message;} public void setMessage(String v){message=v;} public String getType(){return type;} public void setType(String v){type=v;} }
