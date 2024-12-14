package com.hamter.rest;

import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hamter.dto.DoctorDTO;
import com.hamter.mapper.DoctorMapper;
import com.hamter.model.Doctor;
import com.hamter.repository.SpecialtyRepository;
import com.hamter.service.DoctorService;

@RestController
@RequestMapping("/api/doctors")
public class DoctorRestController {

    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private SpecialtyRepository specialtyRepository;
    
    @GetMapping
    @PreAuthorize("hasAnyAuthority('CUST', 'MANAGE')")
    public ResponseEntity<List<DoctorDTO>> getDoctorsBySpecialty(@RequestParam Long specialtyId) {
    	List<Doctor> doctors = doctorService.findDoctorsBySpecialty(specialtyId);
        if (doctors.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<DoctorDTO> doctorDTOs = doctors.stream()
                .map(DoctorMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(doctorDTOs);
    }

    @PreAuthorize("hasAuthority('CUST')")
    @GetMapping("/all-doctor")
    public List<DoctorDTO> getAllDoctor() {
        return doctorService.getAllDoctors().stream()
            .map(DoctorMapper::toDTO)
            .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('CUST')")
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id)
                .map(doctor -> new ResponseEntity<>(doctor, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasAuthority('CUST')")
    @PostMapping("/create-doctor")
    public ResponseEntity<DoctorDTO> createDoctor(@RequestBody DoctorDTO doctorDTO) {
        Doctor doctor = DoctorMapper.toEntity(doctorDTO, specialtyRepository);
        Doctor savedDoctor = doctorService.saveOrUpdateDoctor(doctor);
        DoctorDTO savedDoctorDTO = DoctorMapper.toDTO(savedDoctor);
        return new ResponseEntity<>(savedDoctorDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('CUST')")
    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> updateDoctor(@PathVariable Long id, @RequestBody DoctorDTO doctorDTO) {
        Doctor doctor = DoctorMapper.toEntity(doctorDTO, specialtyRepository);
        doctor.setId(id);
        return doctorService.getDoctorById(id)
                .map(existingDoctor -> {
                    Doctor updatedDoctor = doctorService.saveOrUpdateDoctor(doctor);
                    DoctorDTO updatedDoctorDTO = DoctorMapper.toDTO(updatedDoctor);
                    return new ResponseEntity<>(updatedDoctorDTO, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PreAuthorize("hasAuthority('CUST')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        if (doctorService.getDoctorById(id).isPresent()) {
            doctorService.deleteDoctor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    
}
