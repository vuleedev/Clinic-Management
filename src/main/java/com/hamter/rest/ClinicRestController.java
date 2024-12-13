package com.hamter.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hamter.model.Clinic;
import com.hamter.service.ClinicService;


@RestController
public class ClinicRestController {
	
	@Autowired
	private ClinicService clinicService;
	
	@GetMapping
	public List<Clinic> getAll() {
		return clinicService.getAllClinics();
	}
}
