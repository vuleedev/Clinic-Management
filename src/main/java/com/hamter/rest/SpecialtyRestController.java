package com.hamter.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hamter.model.Specialty;
import com.hamter.service.SpecialtyService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/rest/specialties")
public class SpecialtyRestController {

    @Autowired
    private SpecialtyService specialtyService;

    @GetMapping
    public List<Specialty> getAllSpecialties() {
        return specialtyService.findAll();
    }

    @GetMapping("/{id}")
    public Specialty getSpecialtyById(@PathVariable("id") Long id) {
        return specialtyService.findById(id);
    }

    @PostMapping
    public Specialty createSpecialty(@RequestBody Specialty specialty) {
        return specialtyService.create(specialty);
    }

    @PutMapping("/{id}")
    public Specialty updateSpecialty(@PathVariable("id") Long id, @RequestBody Specialty specialty) {
    	specialty.setId(id);
        return specialtyService.update(specialty);
    }

    @DeleteMapping("/{id}")
    public void deleteSpecialty(@PathVariable("id") Long id) {
        specialtyService.delete(id);
    }
}
