package com.hamter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.model.Clinic;
import com.hamter.repository.ClinicRepository;


@Service
public class ClinicService {
	
	@Autowired
	private ClinicRepository clinicRepository;
	
	public List<Clinic> getAllClinics() {
        return clinicRepository.findAll();
    }
}
