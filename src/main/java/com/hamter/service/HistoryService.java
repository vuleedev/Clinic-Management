package com.hamter.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.dto.HistoryDTO;
import com.hamter.mapper.HistoryMapper;
import com.hamter.model.History;
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
        return historyRepository.findAll()
                .stream()
                .map(HistoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    public HistoryDTO findById(Long id) {
        History history = historyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch sử khám bệnh"));
        return HistoryMapper.toDTO(history);
    }

    public HistoryDTO create(HistoryDTO historyDTO) {
        History history = HistoryMapper.toEntity(historyDTO, userRepository, doctorRepository);
        history.setCreatedAt(new java.util.Date());
        history.setUpdatedAt(new java.util.Date());
        return HistoryMapper.toDTO(historyRepository.save(history));
    }

    public HistoryDTO update(Long id, HistoryDTO historyDTO) {
        History existingHistory = historyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch sử khám bệnh"));
        History history = HistoryMapper.toEntity(historyDTO, userRepository, doctorRepository);
        history.setId(existingHistory.getId());
        history.setCreatedAt(existingHistory.getCreatedAt());
        history.setUpdatedAt(new java.util.Date());
        return HistoryMapper.toDTO(historyRepository.save(history));
    }

    public void delete(Long id) {
        if (!historyRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy lịch sử khám bệnh");
        }
        historyRepository.deleteById(id);
    }

}
