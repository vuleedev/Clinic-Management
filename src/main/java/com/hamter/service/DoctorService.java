package com.hamter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hamter.dto.DoctorDTO;
import com.hamter.mapper.DoctorMapper;
import com.hamter.model.Doctor;
import com.hamter.repository.DoctorRepository;

@Service
public class DoctorService {
	
	@Autowired
    private DoctorRepository doctorRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public DoctorDTO findDoctorByUserId(Long userId) {
	    Doctor doctor = doctorRepository.findByUser_Id(userId);
	    return DoctorMapper.toDTO(doctor);
	}
	
	public Long getDoctorIdByUserId(Long userId) {
	    Doctor doctor = doctorRepository.findByUser_Id(userId);
	    return doctor.getId();
	}
	
	
	public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
	
	public Doctor findById(Long id) {
		return doctorRepository.findById(id).orElse(null);
	}
	
    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    public Doctor saveOrUpdateDoctor(Doctor doctor) {
    	if (doctor.getUser() != null && doctor.getUser().getPassword() != null) {
            doctor.getUser().setPassword(passwordEncoder.encode(doctor.getUser().getPassword()));
        }
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    public List<Doctor> findDoctorsBySpecialty(Long specialtyId) {
        return doctorRepository.findDoctorsBySpecialty(specialtyId);
    }
}
