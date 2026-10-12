package com.example.employee.repository;
import java.util.Optional; import org.springframework.data.jpa.repository.JpaRepository; import com.example.employee.entity.Employee;
public interface EmployeeRepository extends JpaRepository<Employee,Integer> { Optional<Employee> findByEmail(String email); boolean existsByEmail(String email); }
