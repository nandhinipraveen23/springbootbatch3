package com.example.demo.dto;

import com.example.demo.notification.EmailService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.service.EmployeeService;

@Component
public class EmployeeScheduler {
	private final EmailService emailService;
	@Autowired
	EmployeeService employeeService;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
//	@Scheduled(cron="3 33 20 * * *")
//	public void display()
//	{
//		System.out.println("String time: "+ LocalDateTime.now().format(formatter));
//		
//		System.out.println("remainder msg for learn java");
//		
//		System.out.println("Ending time: "+ LocalDateTime.now().format(formatter));
//	}


	EmployeeScheduler(EmailService emailService) {
		this.emailService = emailService;
	}
	
	
//	@Scheduled(cron=" */3 34 20 * * * ")
//	public void display()
//	{
//		System.out.println("String time: "+ LocalDateTime.now().format(formatter));
//		
//		System.out.println("remainder msg for learn java");
//		
//		System.out.println("Ending time: "+ LocalDateTime.now().format(formatter));
//	}
	
	
	
//	@Scheduled(cron=" 0 * * * * * ")
//	public void display()
//	{
//		System.out.println("String time: "+ LocalDateTime.now().format(formatter));
//		
//		System.out.println("remainder msg for learn java");
//		
//		System.out.println("Ending time: "+ LocalDateTime.now().format(formatter));
//	}
	
//	@Scheduled(cron=" 0 1 21 * * * ")
//	public void display()
//	{
//		System.out.println("String time: "+ LocalDateTime.now().format(formatter));
//		
//		System.out.println("remainder msg for learn java");
//		
//		System.out.println("Ending time: "+ LocalDateTime.now().format(formatter));
//	}
	
	
//	@Scheduled(cron=" 0 0 21 1 * * ")
//	public void display()
//	{
//		System.out.println("String time: "+ LocalDateTime.now().format(formatter));
//		
//		System.out.println("remainder msg for learn java");
//		
//		System.out.println("Ending time: "+ LocalDateTime.now().format(formatter));
//	}
	
	
//	@Scheduled(cron=" 0 0 21 1,3,4 * * ")
//	public void display()
//	{
//		System.out.println("String time: "+ LocalDateTime.now().format(formatter));
//		
//		System.out.println("remainder msg for learn java");
//		
//		System.out.println("Ending time: "+ LocalDateTime.now().format(formatter));
//	}
	
//	@Scheduled(cron=" 0 0 0 * * * ")
//	public void display()
//	{
//		System.out.println("String time: "+ LocalDateTime.now().format(formatter));
//		
//		System.out.println("remainder msg for learn java");
//		
//		System.out.println("Ending time: "+ LocalDateTime.now().format(formatter));
//	}
	
	
//	@Scheduled(cron=" 0 0 0 * * 0-4 ")
//	public void display()
//	{
//		System.out.println("String time: "+ LocalDateTime.now().format(formatter));
//		
//		System.out.println("remainder msg for learn java");
//		
//		System.out.println("Ending time: "+ LocalDateTime.now().format(formatter));
//	}
	
	
	@Scheduled(cron=" */10 48 20 * * * ")
	public void display()
	{
		 LocalDate tdy= LocalDate.now();
		 
		 System.out.println(" today is: "+tdy);
		 
	List<EmployeeDto>allemp=	 employeeService.getAllEmployee();
	
	for(EmployeeDto e : allemp)
	{
		if(e.getDoj()==null) {
			continue;
		
		}
		
		if(e.getDoj().getMonthValue()== tdy.getMonthValue() &&
		   e.getDoj().getDayOfMonth()==tdy.getDayOfMonth()	)
		{
			System.out.println(" Hi "+ e.getEmpName()+ "is ur bday");

			String sub=" Happy Birthday";
			String body=" Wishinig u a very happy bday";
			
			emailService.sendEmail("nandhini@gmail.com", e.getEmail(), sub, body);
			
			}
	}
}
}