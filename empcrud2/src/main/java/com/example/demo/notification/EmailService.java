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

    public void sendEmail(
            String from,
            String to,
            String subject,
            String body) {

        try {

            // Create MimeMessage
            MimeMessage message =
                    mailSender.createMimeMessage();

            // Create helper
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            // Set From
            helper.setFrom(from);

            // Set To
            helper.setTo(to);

            // Set Subject
            helper.setSubject(subject);

            // true = HTML email
            helper.setText(body, true);

            // Send email
            mailSender.send(message);

            System.out.println(
                    "Email sent successfully to: " + to
            );

        } catch (Exception e) {

            System.out.println(
                    "Error while sending email: "
                    + e.getMessage()
            );

            e.printStackTrace();
        }
    }
}