package com.hamter.mapper;

import org.modelmapper.ModelMapper;

import com.hamter.dto.HistoryDTO;
import com.hamter.model.History;
import com.hamter.repository.DoctorRepository;
import com.hamter.repository.UserRepository;

public class HistoryMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static HistoryDTO toDTO(History history) {
        return modelMapper.map(history, HistoryDTO.class);
    }

    public static History toEntity(HistoryDTO dto, UserRepository userRepository, DoctorRepository doctorRepository) {
        History history = modelMapper.map(dto, History.class);

        history.setPatient(userRepository.findById(dto.getPatientId()).orElseThrow(() -> new RuntimeException("User not found")));
        history.setDoctor(doctorRepository.findById(dto.getDoctorId()).orElseThrow(() -> new RuntimeException("Doctor not found")));

        return history;
    }
}
