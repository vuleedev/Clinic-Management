package com.hamter.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hamter.dto.SpecialtyDTO;
import com.hamter.mapper.SpecialtyMapper;
import com.hamter.model.Specialty;
import com.hamter.service.SpecialtyService;

@RestController
@RequestMapping("/api/specialties")
public class SpecialtyRestController {

    @Autowired
    private SpecialtyService specialtyService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('STAFF', 'MANAGE', 'CUST')")
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

    @PreAuthorize("hasAnyAuthority('STAFF', 'MANAGE', 'CUST')")
    @GetMapping("/{id}")
    public Specialty getSpecialtyById(@PathVariable("id") Long id) {
        return specialtyService.getSpecialtyById(id);
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'MANAGE', 'CUST')")
    @PostMapping("/create-specialty")
    public Specialty createSpecialty(@RequestBody Specialty specialty) {
        return specialtyService.saveOrUpdateSpecialty(specialty);
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'MANAGE', 'CUST')")
    @PutMapping("/{id}")
    public Specialty updateSpecialty(@PathVariable("id") Long id, @RequestBody Specialty specialty) {
    	specialty.setId(id);
        return specialtyService.saveOrUpdateSpecialty(specialty);
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'MANAGE', 'CUST')")
    @DeleteMapping("/{id}")
    public void deleteSpecialty(@PathVariable("id") Long id) {
    	specialtyService.deleteSpecialty(id);
    }
}
