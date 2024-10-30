package com.hamter.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.repository.HistoryRepository;
import com.hamter.model.History;
import com.hamter.service.HistoryService;

@Service
public class HistoryServiceImpl implements HistoryService {
	
	@Autowired
	private HistoryRepository historyRepository;
	
	@Override
	public List<History> findAll() {
		return historyRepository.findAll() ;
	}

	@Override
	public History findById(Long id) {
		return historyRepository.findById(id).orElse(null);
	}

	@Override
	public History create(History history) {
		history.setCreatedAt(new Date());
		history.setUpdatedAt(new Date());
		return historyRepository.save(history);
	}

	@Override
	public History update(History history) {
		Optional<History> existingHistoryOpt = historyRepository.findById(history.getId());
        if (existingHistoryOpt.isPresent()) {
            History existingHistory = existingHistoryOpt.get();
            existingHistory.setPatientId(history.getPatientId());
            existingHistory.setDoctorId(history.getDoctorId());
            existingHistory.setDescription(history.getDescription());
            existingHistory.setFiles(history.getFiles());
            existingHistory.setUpdatedAt(new Date());
            return historyRepository.save(existingHistory);
        }
		return null;
	}

	@Override
	public void delete(Long id) {
		historyRepository.deleteById(id);
		
	}

}
