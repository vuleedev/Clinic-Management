package com.hamter.rest;

import com.hamter.dto.SpecialtyDTO;
import com.hamter.mapper.SpecialtyMapper;
import com.hamter.model.Specialty;
import com.hamter.service.SpecialtyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/specialties")
public class SpecialtyRestController {

    @Autowired
    private SpecialtyService specialtyService;
    
    @GetMapping
    @PreAuthorize("hasAuthority('CUST')")
    public ResponseEntity<List<SpecialtyDTO>> getAllSpecialties() {
    	List<Specialty> specialties = specialtyService.getAllSpecialties();
        if (specialties.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<SpecialtyDTO> specialtyDTOs = specialties.stream()
                .map(SpecialtyMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(specialtyDTOs);
    }
    
    @PreAuthorize("hasAuthority('CUST')")
    @GetMapping("/{id}")
    public Specialty getSpecialtyById(@PathVariable("id") Long id) {
        return specialtyService.getSpecialtyById(id);
    }
    
    @PreAuthorize("hasAuthority('CUST')")
    @PostMapping("/create-specialty")
    public Specialty createSpecialty(@RequestBody Specialty specialty) {
        return specialtyService.saveOrUpdateSpecialty(specialty);
    }
    
    @PreAuthorize("hasAuthority('CUST')")
    @PutMapping("/{id}")
    public Specialty updateSpecialty(@PathVariable("id") Long id, @RequestBody Specialty specialty) {
    	specialty.setId(id);
        return specialtyService.saveOrUpdateSpecialty(specialty);
    }
    
    @PreAuthorize("hasAuthority('CUST')")
    @DeleteMapping("/{id}")
    public void deleteSpecialty(@PathVariable("id") Long id) {
    	specialtyService.deleteSpecialty(id);
    }
}
