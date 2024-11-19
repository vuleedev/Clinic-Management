package com.hamster.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.hamster.dto.HistoryDTO;
import com.hamster.interfaceService.IHistoryService;
import com.hamster.model.DoctorInfo;
import com.hamster.model.Histories;
import com.hamster.model.User;
import com.hamster.repository.HistoryRepository;

@Service
public class HistoryService implements IHistoryService {

	private final HistoryRepository historyRepository;
	
	@Autowired
    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }
	
	@Override
	public List<Histories> getAllHistories() {
		return historyRepository.findAll();
	}

	@Override
	public Optional<Histories> getHistoryById(Long id) {
		return historyRepository.findById(id);
	}

	@Override
	public Histories createHistory(Histories history) {
		if (!isAuthorizedToEdit()) {
            throw new RuntimeException("You are not authorized to create this history.");
        }

        history.setCreatedAt(new java.util.Date());
        history.setUpdatedAt(new java.util.Date());
        return historyRepository.save(history);
	}

	@Override
	public Histories updateHistory(Long id, Histories history) {
		Optional<Histories> existingHistory = historyRepository.findById(id);
        if (existingHistory.isPresent()) {
            Histories historyToUpdate = existingHistory.get();
            // Kiểm tra quyền sửa
            if (!isAuthorizedToEdit()) {
                throw new RuntimeException("You are not authorized to update this history.");
            }
            historyToUpdate.setDescription(history.getDescription());
            historyToUpdate.setFiles(history.getFiles());
            historyToUpdate.setUpdatedAt(new java.util.Date());
            return historyRepository.save(historyToUpdate);
        } else {
            throw new RuntimeException("History not found with id " + id);
        }
	}

	@Override
	public void deleteHistory(Long id) {
		Optional<Histories> history = historyRepository.findById(id);
        if (history.isPresent()) {
            Histories historyToDelete = history.get();
            // Kiểm tra quyền xóa
            if (!isAuthorizedToEdit()) {
                throw new RuntimeException("You are not authorized to delete this history.");
            }
            historyRepository.delete(historyToDelete);
        } else {
            throw new RuntimeException("History not found with id " + id);
        }
	}

	@Override
	public HistoryDTO convertToDTO(Histories history) {
		return HistoryDTO.builder()
                .id(history.getId())
                .patientId(history.getPatientId().getId())  // Lấy ID từ User
                .doctorId(history.getDoctor().getId())  // Lấy ID từ DoctorInfo
                .description(history.getDescription())
                .files(history.getFiles())
                .createdAt(history.getCreatedAt())
                .updatedAt(history.getUpdatedAt())
                .build();
	}

	@Override
	public Histories convertToEntity(HistoryDTO historyDTO) {
		Histories history = new Histories();
        history.setPatientId(new User());  // Thực thể User (có thể thay đổi)
        history.getPatientId().setId(historyDTO.getPatientId());  // Set ID của patient
        history.setDoctor(new DoctorInfo());  // Thực thể DoctorInfo
        history.getDoctor().setId(historyDTO.getDoctorId());  // Set ID của doctor
        history.setDescription(historyDTO.getDescription());
        history.setFiles(historyDTO.getFiles());
        history.setCreatedAt(historyDTO.getCreatedAt());
        history.setUpdatedAt(historyDTO.getUpdatedAt());
        return history;
	}
	
	private boolean isAuthorizedToEdit() {
        // Kiểm tra quyền người dùng (ADMIN hoặc DOCTOR)
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = userDetails.getAuthorities().toString();
        return role.contains("ROLE_ADMIN") || role.contains("ROLE_DOCTOR");
    }

}
