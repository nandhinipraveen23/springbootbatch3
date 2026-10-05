package com.example.demo.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

 
    
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

     
    public void sendEmail( String from,String to,String subject, String body) {

        try {

            MimeMessage message =
                    mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message);

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

            mailSender.send(message);

            System.out.println("Mail Sent");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
