package com.hamter.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.model.Clinics;
import com.hamter.repository.ClinicRepository;

@Service
public class ClinicService {
  
@Autowired
	private ClinicRepository clinicRepository;
	
	public List<Clinics> findAll() {
		return clinicRepository.findAll();
	}

	public Clinics findById(Long id) {
		return clinicRepository.findById(id).orElse(null);
	}

	public Clinics create(Clinics clinic) {
		return clinicRepository.save(clinic);
	}

	public Clinics update(Clinics clinic) {
		return clinicRepository.save(clinic);
	}

	public void delete(Long id) {
		clinicRepository.deleteById(id);
		
	}
}
