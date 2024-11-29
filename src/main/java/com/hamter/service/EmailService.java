package com.hamter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.hamter.dto.email.EmailDTO;
import com.hamter.service.EmailService;

@Service
public class EmailService {
	
	@Autowired
    private JavaMailSender javaMailSender;
    
    public void SendMailBooking(String toEmail, EmailDTO emailContent) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(emailContent.getSubject());  
        message.setText(emailContent.getBody());      
        javaMailSender.send(message);
    }

}
