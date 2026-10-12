package com.example.notification.service;
import java.util.List; import com.example.notification.dto.*;
public interface NotificationService { NotificationResponseDto create(NotificationRequestDto r); List<NotificationResponseDto> all(); List<NotificationResponseDto> byRecipient(String recipient); }
