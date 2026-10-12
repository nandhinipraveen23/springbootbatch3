package com.example.department.service.impl;
import java.util.*; import org.springframework.stereotype.Service; import com.example.department.dto.*; import com.example.department.entity.Department; import com.example.department.exception.ResourceNotFoundException; import com.example.department.repository.DepartmentRepository; import com.example.department.service.DepartmentService;
@Service public class DepartmentServiceImpl implements DepartmentService { private final DepartmentRepository repo; public DepartmentServiceImpl(DepartmentRepository repo){this.repo=repo;}
 public DepartmentResponseDto create(DepartmentRequestDto r){if(repo.existsByDepartmentName(r.getDepartmentName()))throw new IllegalArgumentException("Department name already exists");Department d=new Department();d.setDepartmentName(r.getDepartmentName());d.setDescription(r.getDescription());return dto(repo.save(d));}
 public List<DepartmentResponseDto> findAll(){return repo.findAll().stream().map(this::dto).toList();}
 public DepartmentResponseDto findById(Long id){return dto(get(id));}
 public DepartmentResponseDto update(Long id,DepartmentRequestDto r){Department d=get(id);if(!d.getDepartmentName().equals(r.getDepartmentName())&&repo.existsByDepartmentName(r.getDepartmentName()))throw new IllegalArgumentException("Department name already exists");d.setDepartmentName(r.getDepartmentName());d.setDescription(r.getDescription());return dto(repo.save(d));}
 public void delete(Long id){repo.delete(get(id));}
 private Department get(Long id){return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Department not found: "+id));}
 private DepartmentResponseDto dto(Department d){return new DepartmentResponseDto(d.getDepartmentId(),d.getDepartmentName(),d.getDescription());}
}
