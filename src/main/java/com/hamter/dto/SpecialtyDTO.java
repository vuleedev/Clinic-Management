package com.hamter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecialtyDTO {
	private Long id;                     
    private String descriptionHTML;     
    private String descriptionMarkdown;  
    private byte[] image;               
    private String name;
}
