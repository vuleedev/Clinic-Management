package com.hamter.service;

import java.util.List;

import com.hamter.model.AllCode;

public interface AllCodeService {
	
	List<AllCode> findAll();
	
	AllCode findById(Long id);
	
	AllCode create(AllCode allCode);
	
	AllCode update(AllCode allCode);
	
	void delete(Long id);
}
