package com.hamter.mapper;

import com.hamter.dto.HistoryDTO;
import com.hamter.model.History;
import com.hamter.repository.DoctorRepository;
import com.hamter.repository.UserRepository;

public class HistoryMapper {

	public static HistoryDTO toDTO(History history) {
        if (history == null) {
            return null;
        }

        HistoryDTO dto = new HistoryDTO();
        dto.setId(history.getId());
        dto.setPatientId(history.getPatient().getId());
        dto.setPatientName(history.getPatient().getUserName());
        dto.setDoctorId(history.getDoctor().getId());
        dto.setDoctorName(history.getDoctor().getName());
        dto.setDescription(history.getDescription());
        dto.setFiles(history.getFiles());
        dto.setCreatedAt(history.getCreatedAt());
        dto.setUpdatedAt(history.getUpdatedAt());

        return dto;
    }

    public static History toEntity(HistoryDTO dto, UserRepository userRepository, DoctorRepository doctorRepository) {
        if (dto == null) {
            return null;
        }

        History history = new History();
        history.setId(dto.getId());
        history.setDescription(dto.getDescription());
        history.setFiles(dto.getFiles());
        history.setPatient(userRepository.findById(dto.getPatientId()).get());
        history.setDoctor(doctorRepository.findById(dto.getDoctorId()).get());

        return history;
    }
}
