package com.example.notification.entity;
import java.time.Instant; import jakarta.persistence.*;
@Entity @Table(name="notifications") public class Notification { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(nullable=false) private String recipient; @Column(nullable=false,length=2000) private String message; private String type; @Column(nullable=false) private Instant createdAt;
 @PrePersist void prePersist(){if(createdAt==null)createdAt=Instant.now();}
 public Notification(){} public Long getId(){return id;} public void setId(Long v){id=v;} public String getRecipient(){return recipient;} public void setRecipient(String v){recipient=v;} public String getMessage(){return message;} public void setMessage(String v){message=v;} public String getType(){return type;} public void setType(String v){type=v;} public Instant getCreatedAt(){return createdAt;} public void setCreatedAt(Instant v){createdAt=v;} }
