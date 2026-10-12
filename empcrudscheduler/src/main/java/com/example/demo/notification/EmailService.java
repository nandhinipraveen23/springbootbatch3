package com.example.demo.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	@Autowired
	JavaMailSender mailSender;
	
	public void sendEmail(String from, String to, String subject, String body)
	{
		
		
		MimeMessage message= mailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		
		try {
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body);
			
			
			
			mailSender.send(message);
			System.out.println("Email Sent");
		} catch (MessagingException e) {
			 
			e.printStackTrace();
		}
		
		
		
	}

}
