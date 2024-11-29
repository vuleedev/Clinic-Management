package com.hamter.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserDTO {
	
	private Long id;
    private String email;
    private String password;
    private String userName;
    private String address;
    private Boolean gender;
    private String phoneNumber;
    private Long roleId;  
    private Date createdAt;
    private Date updatedAt;
}
