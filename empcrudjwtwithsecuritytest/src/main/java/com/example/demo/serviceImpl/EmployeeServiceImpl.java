package com.example.demo.serviceImpl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.EmployeeLoginDto;
import com.example.demo.entity.Employee;
import com.example.demo.exception.EmployeeIdNotFoundException;
import com.example.demo.exception.InvalidUserOrPasswordExcpetion;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;

@Service
public class EmployeeServiceImpl  implements EmployeeService{
	@Autowired
	EmployeeRepository employeeRepository;
	
	public Employee empDtoToEmp(EmployeeDto employeeDto)
	{
		
		System.out.println(" the id id: "+ employeeDto.getEmpId());
		Employee e = new Employee();
		e.setEmpId(employeeDto.getEmpId());
		e.setEmpName(employeeDto.getEmpName());
		e.setEmail(employeeDto.getEmail());
		e.setPassword(employeeDto.getPassword());
		e.setRole(employeeDto.getRole());
		 
		return e;
	}
	public EmployeeDto empToDto(Employee e)
	{
		

		EmployeeDto eDto = new EmployeeDto();
		
		eDto.setEmpId(e.getEmpId());
		eDto.setEmpName(e.getEmpName());
		eDto.setEmail(e.getEmail());
		eDto.setPassword(e.getPassword());
		eDto.setRole(e.getRole());
		return eDto;
				
	}
	
	@Override             // ashwini, ashwini@gmail.com., 1234
	public EmployeeDto insertEmployee(EmployeeDto employeeDto) {
		//convert dto to entity
		
		Employee e =  empDtoToEmp(employeeDto);
		 
		 
		Employee res= employeeRepository.save(e);// save method return the inserted data from the table
		
	 
		
//		employeeDto.setEmpId(e.getEmpId());
		return empToDto(res);
	}

	@Override
	public EmployeeDto updatetEmployee(EmployeeDto employeeDto) throws EmployeeIdNotFoundException {
 
		Employee oldData= employeeRepository.findById(employeeDto.getEmpId()).orElseThrow(()-> new EmployeeIdNotFoundException("Emp ID not found"));
		
		
		if(employeeDto.getEmpName()!=null)
		{
			oldData.setEmpName(employeeDto.getEmpName());
		}

		if(employeeDto.getEmail()!=null)
		{
			oldData.setEmail(employeeDto.getEmail());
		}
		if(employeeDto.getPassword()!=null)
		{
			oldData.setPassword(employeeDto.getPassword());
		}
		
		Employee res=		  employeeRepository.save(oldData);// save method return the inserted data from the table
			
			  

	 
		return empToDto(res);
	}

	@Override
	public List<EmployeeDto> getAllEmployee( ) {
		 
		// ashwini, nandhini
				List<Employee> allEmp =	employeeRepository.findAll();
				
				
				List<EmployeeDto> allEmpDto = new ArrayList<>();
				
				for( Employee e : allEmp)
				{
					
					allEmpDto.add( empToDto(e));
					
				}
		return allEmpDto;
	}

	@Override
	public void deleteEmployee(int empid) throws EmployeeIdNotFoundException {
		Employee e= employeeRepository.findById(empid).orElseThrow(()-> new EmployeeIdNotFoundException("Emp ID not found"));
		
		employeeRepository.delete(e);
	}

	@Override
	public EmployeeDto getEmployeeById(int empid) throws EmployeeIdNotFoundException {
		 
	Employee e=	employeeRepository.findById(empid)
			.orElseThrow(()-> new EmployeeIdNotFoundException("Emp ID not found"));
			
	
	
		return  empToDto(e);
	}
//	@Override
//	public EmployeeDto updateEmp(int empid, Map<String, Object> updates) throws Exception {
//		 
//Employee oldData= employeeRepository.findById(empid).orElseThrow(()-> new Exception("Emp ID not found"));
//		
//		
//		if(updates.containsKey("empName"))
//		{
//			oldData.setEmpName((String)updates.get("empName"));
//		}
//
//		if(updates.containsKey("email"))
//		{
//			oldData.setEmail((String)updates.get("email"));
//		}
//		if(updates.containsKey("password"))
//		{
//			oldData.setPassword((String)updates.get("password"));
//		}
//		
//		Employee res=		  employeeRepository.save(oldData);// save method return the inserted data from the table
//			
//			  
//
//	 
//		return empToDto(res);
//		
//		
 	 
//	}
	
	
	@Override// using reflection API
	public EmployeeDto updateEmp(int empid, Map<String, Object> updates) throws EmployeeIdNotFoundException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		 
		System.out.println("im gettinf called");
Employee oldData= employeeRepository.findById(empid).orElseThrow(()-> new EmployeeIdNotFoundException("Emp ID not found"));
		
System.out.println(updates.entrySet());
for (Map.Entry<String, Object> entry : updates.entrySet())
{
	System.out.println(" im inside the map");
	String filedName= entry.getKey(); 
	Object value=entry.getValue();
	
	System.out.println(" filed +" + "val: "+ filedName +" "+ value);
	Field actualFieldName= Employee.class.getDeclaredField(filedName);
	actualFieldName.setAccessible(true);
	
	actualFieldName.set(oldData, value);
	 
}


		 
		Employee res=		  employeeRepository.save(oldData);// save method return the inserted data from the table
			
			  

	 
		return empToDto(res);
		
		

		 
	}
	@Override
	public EmployeeLoginDto checkLogin(EmployeeLoginDto employeeLoginDto) throws InvalidUserOrPasswordExcpetion {
		 
	Employee emp=	employeeRepository.login(employeeLoginDto.getEmail(),employeeLoginDto.getPassword())
			.orElseThrow(()-> new InvalidUserOrPasswordExcpetion("Invalid email or password"));
	
	System.err.println("im from check: "+ emp.getEmail()+" "+emp.getRole());
	EmployeeLoginDto edt= new EmployeeLoginDto();
	
	
	edt.setEmail(emp.getEmail());
	edt.setRole(emp.getRole());
	System.err.println(edt.getEmail());
	System.err.println(edt.getRole());
	
	  return edt;
	}
	
	
	

}
