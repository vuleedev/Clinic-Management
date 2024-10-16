package com.hamter.service;

import java.util.List;

import com.hamter.model.History;

public interface HistoryService {
	
	List<History> findAll();
	
	History findById(Long id);
	
	History create(History history);
	
	History update(History history);
	
	void delete(Long id);
}
