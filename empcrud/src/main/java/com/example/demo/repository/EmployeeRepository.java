package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
/*
 * EmpDao
 * void inserEmp(Emp emp);
 * List<Emp> getAllEmp();
 * 
 * 
 * EmpDaoImpl
 * 
 * void insertEmp(Emp emp)
 * {
 * 
 *  session.save(emp)
 * }
 * 
 * List<Emp> getAllEmp()
 * {
 * 
 * session.findAll();
 * }
 */
}
