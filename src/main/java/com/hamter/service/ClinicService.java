package com.hamter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.repository.ClinicRepository;
import com.hamter.model.Clinic;
import com.hamter.service.ClinicService;

@Service
public class ClinicService {
	
	@Autowired
	private ClinicRepository clinicRepository;
	
	public List<Clinic> findAll() {
		return clinicRepository.findAll();
	}

	public Clinic findById(Long id) {
		return clinicRepository.findById(id).orElse(null);
	}

	public Clinic create(Clinic clinic) {
		return clinicRepository.save(clinic);
	}

	public Clinic update(Clinic clinic) {
		return clinicRepository.save(clinic);
	}

	public void delete(Long id) {
		clinicRepository.deleteById(id);
		
	}

}
