package com.hamter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
	private Long id;                  
    private String email;             
    private String password;          
    private String firstName;         
    private String lastName;          
    private String address;           
    private String gender;            
    private String roleId;            
    private String phoneNumber;       
    private String positionId;        
    private byte[] image;             
    private String tokenUser;         
    private Integer status;
}
