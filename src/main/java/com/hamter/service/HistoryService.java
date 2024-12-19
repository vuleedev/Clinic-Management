package com.hamter.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.dto.BookingDTO;
import com.hamter.dto.HistoryDTO;
import com.hamter.mapper.BookingMapper;
import com.hamter.mapper.HistoryMapper;
import com.hamter.model.Booking;
import com.hamter.model.History;
import com.hamter.model.User;
import com.hamter.repository.DoctorRepository;
import com.hamter.repository.HistoryRepository;
import com.hamter.repository.UserRepository;

@Service
public class HistoryService {

	@Autowired
	private HistoryRepository historyRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	public List<HistoryDTO> findAll() {
		return historyRepository.findAll().stream().map(HistoryMapper::toDTO).collect(Collectors.toList());
	}

	public HistoryDTO findById(Long id) {
		History history = historyRepository.findById(id).orElse(null);
		return HistoryMapper.toDTO(history);
	}
	
	public HistoryDTO findHistoryByUserId(Long userId) {
		History history = historyRepository.findByUser_Id(userId);
		return HistoryMapper.toDTO(history);
	}
	
	public List<HistoryDTO> findHistoryByUser(Long userId) {
        List<History> history = historyRepository.findAllByUser_Id(userId);
        return history.stream()
        		.map(HistoryMapper::toDTO)
                .collect(Collectors.toList());
    }
	
	public HistoryDTO create(HistoryDTO historyDTO, Long userId) {
		History history = HistoryMapper.toEntity(historyDTO, userRepository, doctorRepository);
		User user = userRepository.findById(userId).orElse(null);
		history.setUser(user);
		History savedHistory = historyRepository.save(history);
		return HistoryMapper.toDTO(savedHistory);
	}

	public HistoryDTO update(Long id, HistoryDTO historyDTO) {
		History existingHistory = historyRepository.findById(id).orElse(null);
		History history = HistoryMapper.toEntity(historyDTO, userRepository, doctorRepository);
		history.setId(existingHistory.getId());
		history.setCreatedAt(existingHistory.getCreatedAt());
		history.setUpdatedAt(new java.util.Date());
		return HistoryMapper.toDTO(historyRepository.save(history));
	}

	public void delete(Long id) {
		historyRepository.deleteById(id);
	}

	public List<HistoryDTO> findAllHistoriesByDoctorId(Long doctorId) {
		List<History> histories = historyRepository.findByDoctorId(doctorId);
		return histories.stream().map(HistoryMapper::toDTO).collect(Collectors.toList());
	}

}
