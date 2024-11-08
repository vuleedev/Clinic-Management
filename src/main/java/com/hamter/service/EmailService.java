package com.hamter.service;

public interface EmailService {
	
	public void SendMailBooking(String toEmail, String subject, String body);
}
