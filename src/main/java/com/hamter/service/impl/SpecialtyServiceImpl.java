package com.hamter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.repository.SpecialtyRepository;
import com.hamter.model.Specialty;
import com.hamter.service.SpecialtyService;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {
	@Autowired
	private SpecialtyRepository specialtyRepository;
	
	@Override
	public List<Specialty> findAll() {
		return specialtyRepository.findAll();
	}

	@Override
	public Specialty findById(Long id) {
		return specialtyRepository.findById(id).orElse(null);
	}

	@Override
	public Specialty create(Specialty specialty) {
		return specialtyRepository.save(specialty);
	}

	@Override
	public Specialty update(Specialty specialty) {
		return specialtyRepository.save(specialty);
	}

	@Override
	public void delete(Long id) {
		specialtyRepository.deleteById(id);
	}

}
