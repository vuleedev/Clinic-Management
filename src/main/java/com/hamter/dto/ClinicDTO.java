package com.hamter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClinicDTO {
	private Long id;                   
    private String name;            
    private String address;         
    private String descriptionMarkdown; 
    private String descriptionHTML;      
    private byte[] image;
}
