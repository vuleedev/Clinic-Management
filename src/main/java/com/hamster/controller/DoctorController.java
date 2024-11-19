package com.hamster.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.hamster.dto.DoctorInfoDTO;
import com.hamster.model.DoctorInfo;
import com.hamster.service.DoctorInfoService;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
	private final DoctorInfoService doctorService;
	
	@Autowired
    public DoctorController(DoctorInfoService doctorService) {
        this.doctorService = doctorService;
    }
	
	@PreAuthorize("hasAnyRole('USER', 'DOCTOR', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<DoctorInfoDTO>> getAllDoctors() {
        List<DoctorInfoDTO> doctors = doctorService.getAllDoctors();
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }
	
	@PreAuthorize("hasAnyRole('USER', 'DOCTOR', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<DoctorInfoDTO> getDoctorById(@PathVariable Long id) {
		DoctorInfoDTO doctorInfoDTO = doctorService.getDoctorById(id)
	            .orElseThrow(() -> new RuntimeException("Doctor not found with id " + id));
	    return ResponseEntity.ok(doctorInfoDTO);
    }
	
	// Thêm thông tin bác sĩ - Chỉ admin có thể thêm
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<DoctorInfoDTO> createDoctor(@RequestBody DoctorInfoDTO doctorInfoDTO) {
        DoctorInfoDTO newDoctor = doctorService.createDoctor(doctorInfoDTO);
        return new ResponseEntity<>(newDoctor, HttpStatus.CREATED);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<DoctorInfoDTO> updateDoctor(@PathVariable Long id, @RequestBody DoctorInfoDTO doctorInfoDTO) {
        DoctorInfoDTO updatedDoctor = doctorService.updateDoctor(id, doctorInfoDTO);
        return new ResponseEntity<>(updatedDoctor, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>("Doctor deleted successfully.", HttpStatus.OK);
    }
}
