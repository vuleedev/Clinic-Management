package com.hamter.dto.auth;

import lombok.Data;

@Data
public class ResponseMessage {
	private String message;
    private String status;
    private String url;

    public ResponseMessage(String message, String status, String url) {
        this.message = message;
        this.status = status;
        this.url = url;
    }
}
