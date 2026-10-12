package com.example.employee.dto;
public class EmployeeResponseDto {
 private Integer empno; private String name; private String email; private Long departmentId; private String departmentName;
 public EmployeeResponseDto() {}
 public EmployeeResponseDto(Integer empno,String name,String email,Long departmentId,String departmentName){this.empno=empno;this.name=name;this.email=email;this.departmentId=departmentId;this.departmentName=departmentName;}
 public Integer getEmpno(){return empno;} public void setEmpno(Integer v){empno=v;}
 public String getName(){return name;} public void setName(String v){name=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;}
 public Long getDepartmentId(){return departmentId;} public void setDepartmentId(Long v){departmentId=v;}
 public String getDepartmentName(){return departmentName;} public void setDepartmentName(String v){departmentName=v;}
}
