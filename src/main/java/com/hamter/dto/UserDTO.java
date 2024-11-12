package com.hamter.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hamter.model.Roles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
	private Long id;                  
    private String email;             
    private String password;   
    private String retypePassword;
    private String firstName;         
    private String lastName;          
    private String address;           
    private Boolean gender;            
    private String roleId;            
    private String phoneNumber;       
    private String positionId;        
    private String image;             
    private String tokenUser;         
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    private Date updatedAt;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRetypePassword() {
		return retypePassword;
	}

	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}
    
    
}
