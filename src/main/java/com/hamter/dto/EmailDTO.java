package com.hamter.dto;

import lombok.Data;

@Data
public class EmailDTO {
	private String subject;
    private String body;
    
    public EmailDTO(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }
}
