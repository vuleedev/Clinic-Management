package com.hamter.service;

import java.util.List;

import com.hamter.model.Specialty;

public interface SpecialtyService {
	
	List<Specialty> findAll();
	
	Specialty findById(Long id);
	
	Specialty create (Specialty specialty);
	
	Specialty update (Specialty specialty);
	
	void delete (Long id);
}
