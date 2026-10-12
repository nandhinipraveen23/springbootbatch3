package com.example.notification.service.impl;
import java.util.List; import org.springframework.stereotype.Service; import com.example.notification.dto.*; import com.example.notification.entity.Notification; import com.example.notification.repository.NotificationRepository; import com.example.notification.service.NotificationService;
@Service public class NotificationServiceImpl implements NotificationService {private final NotificationRepository repo;public NotificationServiceImpl(NotificationRepository r){repo=r;}
 public NotificationResponseDto create(NotificationRequestDto r){Notification n=new Notification();n.setRecipient(r.getRecipient());n.setMessage(r.getMessage());n.setType(r.getType()==null?"GENERAL":r.getType());return dto(repo.save(n));}
 public List<NotificationResponseDto> all(){return repo.findAll().stream().map(this::dto).toList();} public List<NotificationResponseDto> byRecipient(String recipient){return repo.findByRecipientOrderByCreatedAtDesc(recipient).stream().map(this::dto).toList();}
 private NotificationResponseDto dto(Notification n){return new NotificationResponseDto(n.getId(),n.getRecipient(),n.getMessage(),n.getType(),n.getCreatedAt());}}
