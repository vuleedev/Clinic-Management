package com.hamter.rest;

import com.hamter.model.Specialty;
import com.hamter.model.User;
import com.hamter.service.SpecialtyService;
import com.hamter.util.JwTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialties")
public class SpecialtyRestController {

    @Autowired
    private SpecialtyService specialtyService;

    @Autowired
    private JwTokenUtil jwTokenUtil;
    
    @GetMapping
    public List<Specialty> getAllSpecialtys() {
        return specialtyService.getAllSpecialties();
    }

    @GetMapping("/{id}")
    public Specialty getSpecialtyById(@PathVariable("id") Long id) {
        return specialtyService.getSpecialtyById(id);
    }

    @PostMapping
    public Specialty createSpecialty(@RequestBody Specialty specialty) {
        return specialtyService.saveOrUpdateSpecialty(specialty);
    }

    @PutMapping("/{id}")
    public Specialty updateSpecialty(@PathVariable("id") Long id, @RequestBody Specialty specialty) {
    	specialty.setId(id);
        return specialtyService.saveOrUpdateSpecialty(specialty);
    }

    @DeleteMapping("/{id}")
    public void deleteSpecialty(@PathVariable("id") Long id) {
    	specialtyService.deleteSpecialty(id);
    }
}
