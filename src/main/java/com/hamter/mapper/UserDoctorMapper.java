package com.hamter.mapper;

import org.modelmapper.ModelMapper;

import com.hamter.dto.UserDoctorDTO;
import com.hamter.model.Doctor;
import com.hamter.model.Role;
import com.hamter.model.User;
import com.hamter.repository.RoleRepository;
import com.hamter.repository.SpecialtyRepository;

public class UserDoctorMapper {

	private static final ModelMapper modelMapper = new ModelMapper();

    public static Doctor toEntity(UserDoctorDTO dto, SpecialtyRepository specialtyRepository, RoleRepository roleRepository) {
        Doctor doctor = modelMapper.map(dto, Doctor.class);

        doctor.setSpecialty(specialtyRepository.findById(dto.getSpecialtyId()).orElse(null));

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); 
        
        Role doctorRole = roleRepository.findById(2L).orElse(null); 
        user.setRole(doctorRole); 

        doctor.setUser(user);

        doctor.setUser(user);

        return doctor;
    }
}
