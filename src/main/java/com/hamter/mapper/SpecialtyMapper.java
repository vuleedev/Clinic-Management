package com.hamter.mapper;

import com.hamter.dto.SpecialtyDTO;
import com.hamter.model.Specialty;

public class SpecialtyMapper {
	
	public static SpecialtyDTO toDTO(Specialty specialty) {
        SpecialtyDTO dto = new SpecialtyDTO();
        dto.setId(specialty.getId());
        dto.setName(specialty.getName());
        dto.setDescription(specialty.getDescription());
        dto.setImage(specialty.getImage());
        dto.setCreatedAt(specialty.getCreatedAt());
        dto.setUpdatedAt(specialty.getUpdatedAt());
        return dto;
    }

    public static Specialty toEntity(SpecialtyDTO dto) {
        Specialty specialty = new Specialty();
        specialty.setId(dto.getId());
        specialty.setName(dto.getName());
        specialty.setDescription(dto.getDescription());
        specialty.setImage(dto.getImage());
        return specialty;
    }
}
