package com.hamter.dto.auth;

import lombok.Data;

@Data
public class CPasswordRequest {
	private String email;
    private String oldPassword;
    private String newPassword;

}
