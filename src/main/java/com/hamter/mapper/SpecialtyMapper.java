package com.hamter.mapper;

import org.modelmapper.ModelMapper;

import com.hamter.dto.SpecialtyDTO;
import com.hamter.model.Specialty;

public class SpecialtyMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static SpecialtyDTO toDTO(Specialty specialty) {
        SpecialtyDTO specialtyDTO = modelMapper.map(specialty, SpecialtyDTO.class);
        return specialtyDTO;
    }

    public static Specialty toEntity(SpecialtyDTO specialtyDTO) {
        return modelMapper.map(specialtyDTO, Specialty.class);
    }
}
