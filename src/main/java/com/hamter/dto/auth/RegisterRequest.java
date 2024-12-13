package com.hamter.dto.auth;

import lombok.Data;

@Data
public class RegisterRequest {

	private String email;
    private String password;

    private String userName;
    private Boolean gender;
    private String address;
}
