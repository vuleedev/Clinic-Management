package com.hamter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.model.Specialty;
import com.hamter.repository.SpecialtyRepository;

@Service
public class SpecialtyService {

    @Autowired
    private SpecialtyRepository specialtyRepository;

    public List<Specialty> getAllSpecialties() {
        return specialtyRepository.findAll();
    }

    public Specialty getSpecialtyById(Long id) {
        return specialtyRepository.findById(id).orElse(null);
    }

    public Specialty saveOrUpdateSpecialty(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    public void deleteSpecialty(Long id) {
        specialtyRepository.deleteById(id);
    }
}
