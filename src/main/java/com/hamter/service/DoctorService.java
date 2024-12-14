package com.hamter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.model.Doctor;
import com.hamter.repository.DoctorRepository;

@Service
public class DoctorService {

	@Autowired
    private DoctorRepository doctorRepository;

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
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    public List<Doctor> findDoctorsBySpecialty(Long specialtyId) {
        return doctorRepository.findDoctorsBySpecialty(specialtyId);
    }
}
