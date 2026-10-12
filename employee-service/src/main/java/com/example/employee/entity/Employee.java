package com.example.employee.entity;
import jakarta.persistence.*;
@Entity @Table(name="employees2", uniqueConstraints=@UniqueConstraint(columnNames="email"))

 
public class Employee {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Integer empno;
 @Column(name="name", nullable=false) private String name;
 @Column(nullable=false, unique=true) private String email;
 @Column(nullable=false) private String password;
 private Long departmentId;
 public Employee() {}
 public Integer getEmpno(){return empno;} public void setEmpno(Integer empno){this.empno=empno;}
 public String getName(){return name;} public void setName(String name){this.name=name;}
 public String getEmail(){return email;} public void setEmail(String email){this.email=email;}
 public String getPassword(){return password;} public void setPassword(String password){this.password=password;}
 public Long getDepartmentId(){return departmentId;} public void setDepartmentId(Long departmentId){this.departmentId=departmentId;}
}
