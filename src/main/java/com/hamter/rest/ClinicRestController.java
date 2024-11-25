package com.hamter.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hamter.model.Clinic;
import com.hamter.service.ClinicService;

@RestController
@RequestMapping("/api/clinic")
public class ClinicRestController {
	
	@Autowired
	private ClinicService clinicService;
	
	@GetMapping
    public List<Clinic> getAllClinic() {
        return clinicService.findAll();
    }

    @GetMapping("/{id}")
    public Clinic getClinicById(@PathVariable("id") Long id) {
        return clinicService.findById(id);
    }

    @PostMapping
    public Clinic createClinic(@RequestBody Clinic clinic) {
        return clinicService.create(clinic);
    }

    @PutMapping("/{id}")
    public Clinic updateClinic(@PathVariable("id") Long id, @RequestBody Clinic clinic) {
    	clinic.setId(id);
        return clinicService.update(clinic);
    }

    @DeleteMapping("/{id}")
    public void deleteClinic(@PathVariable("id") Long id) {
        clinicService.delete(id);
    }
	
}
