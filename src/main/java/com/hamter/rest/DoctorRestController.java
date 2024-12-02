package com.hamter.rest;

import com.hamter.dto.DoctorDTO;
import com.hamter.mapper.DoctorMapper;
import com.hamter.model.Doctor;
import com.hamter.service.DoctorService;
import com.hamter.util.JwTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/doctors")
public class DoctorRestController {

    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private JwTokenUtil jwTokenUtil;
    
    @GetMapping
    @PreAuthorize("hasAuthority('CUST')")
    public ResponseEntity<List<DoctorDTO>> getDoctorsBySpecialty(@RequestParam Long specialtyId, @RequestHeader("Authorization") String authorizationHeader) {
    	Long userId = getUserIdFromToken(authorizationHeader);
    	List<Doctor> doctors = doctorService.findDoctorsBySpecialty(specialtyId);
        if (doctors.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<DoctorDTO> doctorDTOs = doctors.stream()
                .map(DoctorMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(doctorDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id)
                .map(doctor -> new ResponseEntity<>(doctor, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        Doctor savedDoctor = doctorService.saveOrUpdateDoctor(doctor);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        return doctorService.getDoctorById(id)
                .map(existingDoctor -> {
                    doctor.setId(id); 
                    Doctor updatedDoctor = doctorService.saveOrUpdateDoctor(doctor);
                    return new ResponseEntity<>(updatedDoctor, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        if (doctorService.getDoctorById(id).isPresent()) {
            doctorService.deleteDoctor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    private Long getUserIdFromToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7);
            return jwTokenUtil.extractUserId(jwtToken);
        }
        throw new RuntimeException("Không tìm thấy token");
    }
}
