package com.hamter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarkdownDTO {
	private Long id;             
    private String contentHTML;  
    private String contentMarkdown;
    private String description;    
    private Long doctorId;         
    private Long specialtyId;    
    private Long clinicId;
}
