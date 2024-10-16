package com.hamter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.repository.ClinicRepository;
import com.hamter.model.Clinic;
import com.hamter.service.ClinicService;

@Service
public class ClinicServiceImpl implements ClinicService {
	
	@Autowired
	private ClinicRepository clinicRepository;
	
	@Override
	public List<Clinic> findAll() {
		return clinicRepository.findAll();
	}

	@Override
	public Clinic findById(Long id) {
		return clinicRepository.findById(id).orElse(null);
	}

	@Override
	public Clinic create(Clinic clinic) {
		return clinicRepository.save(clinic);
	}

	@Override
	public Clinic update(Clinic clinic) {
		return clinicRepository.save(clinic);
	}

	@Override
	public void delete(Long id) {
		clinicRepository.deleteById(id);
		
	}

}
