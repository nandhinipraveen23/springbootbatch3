package com.example.notification.controller;
import java.util.List; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import jakarta.validation.Valid; import com.example.notification.dto.*; import com.example.notification.service.NotificationService;
@RestController @RequestMapping("/notifications") public class NotificationController {private final NotificationService service;public NotificationController(NotificationService s){service=s;}
 @PostMapping public ResponseEntity<NotificationResponseDto> create(@Valid @RequestBody NotificationRequestDto r){return ResponseEntity.status(HttpStatus.CREATED).body(service.create(r));}
 @GetMapping public List<NotificationResponseDto> all(){return service.all();} @GetMapping("/recipient/{email}") public List<NotificationResponseDto> byRecipient(@PathVariable String email){return service.byRecipient(email);}
}
