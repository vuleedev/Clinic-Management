package com.hamter.mapper;

import com.hamter.dto.DoctorDTO;
import com.hamter.model.Doctor;

public class DoctorMapper {

	public static DoctorDTO toDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setEmail(doctor.getEmail());
        dto.setPhoneNumber(doctor.getPhoneNumber());
        dto.setGender(doctor.getGender());
        dto.setProfilePicture(doctor.getProfilePicture());
        dto.setSpecialtyId(doctor.getSpecialty().getId());
        dto.setCreatedAt(doctor.getCreatedAt());
        dto.setUpdatedAt(doctor.getUpdatedAt());
        return dto;
    }

    public static Doctor toEntity(DoctorDTO dto) {
        Doctor doctor = new Doctor();
        doctor.setId(dto.getId());
        doctor.setName(dto.getName());
        doctor.setEmail(dto.getEmail());
        doctor.setPhoneNumber(dto.getPhoneNumber());
        doctor.setGender(dto.getGender());
        doctor.setProfilePicture(dto.getProfilePicture());
        return doctor;
    }
}
