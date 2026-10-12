package com.example.department.entity;
import jakarta.persistence.*;
@Entity @Table(name="departments") public class Department { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long departmentId; @Column(nullable=false,unique=true) private String departmentName; private String description;
 public Department(){} public Long getDepartmentId(){return departmentId;} public void setDepartmentId(Long v){departmentId=v;} public String getDepartmentName(){return departmentName;} public void setDepartmentName(String v){departmentName=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;} }
