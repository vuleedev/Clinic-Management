package com.hamter.rest;

import com.hamter.dto.SpecialtyDTO;
import com.hamter.mapper.SpecialtyMapper;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/specialties")
public class SpecialtyRestController {

    @Autowired
    private SpecialtyService specialtyService;

    @Autowired
    private JwTokenUtil jwTokenUtil;
    
    @GetMapping
    @PreAuthorize("hasAuthority('CUST')")
    public ResponseEntity<List<SpecialtyDTO>> getAllSpecialties(@RequestHeader("Authorization") String authorizationHeader) {
    	Long userId = getUserIdFromToken(authorizationHeader);
    	List<Specialty> specialties = specialtyService.getAllSpecialties();
        if (specialties.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<SpecialtyDTO> specialtyDTOs = specialties.stream()
                .map(SpecialtyMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(specialtyDTOs);
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
    
    private Long getUserIdFromToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7);
            return jwTokenUtil.extractUserId(jwtToken);
        }
        throw new RuntimeException("Không tìm thấy token");
    }
}
