package com.hamter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.repository.SpecialtyRepository;
import com.hamter.model.Specialty;

@Service
public class SpecialtyService {
	
	@Autowired
	private SpecialtyRepository specialtyRepository;
	
	public List<Specialty> findAll() {
		return specialtyRepository.findAll();
	}

	public Specialty findById(Long id) {
		return specialtyRepository.findById(id).orElse(null);
	}

	public Specialty create(Specialty specialty) {
		return specialtyRepository.save(specialty);
	}

	public Specialty update(Specialty specialty) {
		return specialtyRepository.save(specialty);
	}

	public void delete(Long id) {
		specialtyRepository.deleteById(id);
	}

}
