package com.hamter.mapper;

import org.modelmapper.ModelMapper;

import com.hamter.dto.DoctorDTO;
import com.hamter.model.Doctor;
import com.hamter.repository.SpecialtyRepository;

public class DoctorMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static DoctorDTO toDTO(Doctor doctor) {
        return modelMapper.map(doctor, DoctorDTO.class);
    }

    public static Doctor toEntity(DoctorDTO dto, SpecialtyRepository specialtyRepository) {
        Doctor doctor = modelMapper.map(dto, Doctor.class);

        doctor.setSpecialty(specialtyRepository.findById(dto.getSpecialtyId()).orElse(null));

        return doctor;
    }
}
