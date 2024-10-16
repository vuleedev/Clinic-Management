package com.hamter.service.impl;

import java.util.List;

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
		return historyRepository.save(history);
	}

	@Override
	public History update(History history) {
		return historyRepository.save(history);
	}

	@Override
	public void delete(Long id) {
		historyRepository.deleteById(id);
		
	}

}
