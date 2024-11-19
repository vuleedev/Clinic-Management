package com.hamster.mapper;

import com.hamster.dto.DoctorInfoDTO;
import com.hamster.model.DoctorInfo;

public class DoctorInfoMapper {
	public static DoctorInfoDTO convertToDTO(DoctorInfo doctorInfo) {
        return DoctorInfoDTO.builder()
                .id(doctorInfo.getId())
                .name(doctorInfo.getName())
                .email(doctorInfo.getEmail())
                .phoneNumber(doctorInfo.getPhoneNumber())
                .gender(doctorInfo.getGender())
                .profilePicture(doctorInfo.getProfilePicture())
                .createdAt(doctorInfo.getCreatedAt())
                .updatedAt(doctorInfo.getUpdatedAt())
                .specialtyId(doctorInfo.getSpecialty() != null ? doctorInfo.getSpecialty().getId() : null)
                .specialtyName(doctorInfo.getSpecialty() != null ? doctorInfo.getSpecialty().getName() : null)
                .build();
    }
}
