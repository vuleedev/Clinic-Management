package com.hamster.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hamster.model.Clinics;
import com.hamster.service.ClinicService;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/rest/clinic")
public class ClinicController {
  
	@Autowired
	private ClinicService clinicService;

	@GetMapping
	public List<Clinics> getAllClinic() {
        return clinicService.findAll();
    }

    @GetMapping("/{id}")
    public Clinics getClinicById(@PathVariable("id") Long id) {
        return clinicService.findById(id);
    }

    @PostMapping
    public Clinics createClinic(@RequestBody Clinics clinic) {
        return clinicService.create(clinic);
    }

    @PutMapping("/{id}")
    public Clinics updateClinic(@PathVariable("id") Long id, @RequestBody Clinics clinic) {
    	clinic.setId(id);
        return clinicService.update(clinic);
    }

    @DeleteMapping("/{id}")
    public void deleteClinic(@PathVariable("id") Long id) {
        clinicService.delete(id);
    }
}
