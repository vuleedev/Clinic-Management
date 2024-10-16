package com.hamter.service;

import java.util.List;

import com.hamter.model.Clinic;

public interface ClinicService {
	
	List<Clinic> findAll();
	
	Clinic findById(Long id);
	
	Clinic create(Clinic clinic);
	
	Clinic update(Clinic clinic);
	
	void delete(Long id);
}
