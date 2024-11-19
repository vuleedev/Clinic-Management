package com.hamster.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.hamster.dto.SpecialtyDTO;
import com.hamster.interfaceService.ISpecialtyService;
import com.hamster.model.Specialties;
import com.hamster.repository.SpecialtyRepository;

public class SpecialtyService implements ISpecialtyService {
	
	@Autowired
	private SpecialtyRepository specialtyRepository;

	@Override
	public List<SpecialtyDTO> getAllSpecialty() {
		return specialtyRepository.findAll().stream()
	            .map(this::convertToDTO)
	            .collect(Collectors.toList());	
	}

	@Override
	public SpecialtyDTO createSpecialty(SpecialtyDTO specialtyDTO) throws Exception {
		Specialties specialty = convertToEntity(specialtyDTO);
        specialty.setCreatedAt(new Date());
        specialty.setUpdatedAt(new Date());
        return convertToDTO(specialtyRepository.save(specialty));
	}

	@Override
	public SpecialtyDTO updateSpecialty(Long id, SpecialtyDTO specialtyDTO) {
		Optional<Specialties> existingSpecialty = specialtyRepository.findById(id);
        if (existingSpecialty.isPresent()) {
            Specialties specialty = existingSpecialty.get();
            specialty.setName(specialtyDTO.getName());
            specialty.setDescription(specialtyDTO.getDescription());
            specialty.setImage(specialtyDTO.getImage());
            specialty.setUpdatedAt(new Date());
            return convertToDTO(specialtyRepository.save(specialty));
        }
        return null;
	}

	@Override
	public void deleteSpecialty(Long id) {
		specialtyRepository.deleteById(id);		
	}
	
	private SpecialtyDTO convertToDTO(Specialties specialty) {
        return SpecialtyDTO.builder()
            .id(specialty.getId())
            .name(specialty.getName())
            .description(specialty.getDescription())
            .image(specialty.getImage())
            .createdAt(specialty.getCreatedAt())
            .updatedAt(specialty.getUpdatedAt())
            .build();
    }

    private Specialties convertToEntity(SpecialtyDTO specialtyDTO) {
        Specialties specialty = new Specialties();
        specialty.setId(specialtyDTO.getId());
        specialty.setName(specialtyDTO.getName());
        specialty.setDescription(specialtyDTO.getDescription());
        specialty.setImage(specialtyDTO.getImage());
        specialty.setCreatedAt(specialtyDTO.getCreatedAt());
        specialty.setUpdatedAt(specialtyDTO.getUpdatedAt());
        return specialty;
    }

	@Override
	public Optional<SpecialtyDTO> getSpecialtyById(Long id) {
		return specialtyRepository.findById(id).map(this::convertToDTO);
	}

}
