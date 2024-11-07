package com.hamter.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllcodeDTO {
	private Long id;           
    private String keyMap;    
    private String type;      
    private String valueEn;   
    private String valueVi;   
    private Date createdAt;  
    private Date updatedAt; 
}
