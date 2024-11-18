package com.hamter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.repository.HistoryRepository;
import com.hamter.model.History;

@Service
public class HistoryService {
	
	@Autowired
	private HistoryRepository historyRepository;
	
	public List<History> findAll() {
		return historyRepository.findAll() ;
	}

	public History findById(Long id) {
		return historyRepository.findById(id).orElse(null);
	}

	public History create(History history) {
		return historyRepository.save(history);
	}

	public History update(History history) {
		return historyRepository.save(history);
	}

	public void delete(Long id) {
		historyRepository.deleteById(id);
		
	}

}
