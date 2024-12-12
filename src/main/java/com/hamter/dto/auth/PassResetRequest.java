package com.hamter.dto.auth;

import lombok.Data;

@Data
public class PassResetRequest {
	private String token;
    private String newPassword;
}
