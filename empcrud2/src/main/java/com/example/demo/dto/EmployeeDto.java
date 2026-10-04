package com.example.demo.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EmployeeDto {

    private Integer empId;

    private String empName;

    private String email;

    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate doj;


    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }


    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }


    public LocalDate getDoj() {
        return doj;
    }

    public void setDoj(LocalDate doj) {
        this.doj = doj;
    }


    @Override
    public String toString() {

        return "EmployeeDto [empId=" + empId
                + ", empName=" + empName
                + ", email=" + email
                + ", dob=" + dob
                + ", doj=" + doj
                + "]";
    }
}