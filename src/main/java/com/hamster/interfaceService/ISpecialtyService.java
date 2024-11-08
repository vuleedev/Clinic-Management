package com.hamster.interfaceService;

import java.util.List;
import java.util.Optional;
import com.hamter.dto.SpecialtyDTO;

public interface ISpecialtyService {
	List<SpecialtyDTO> getAllSpecialty();
	
	SpecialtyDTO createSpecialty(SpecialtyDTO specialtyDTO) throws Exception;
	
	SpecialtyDTO updateSpecialty(Long id, SpecialtyDTO specialtyDTO);
	
	List<SpecialtyDTO> getAllSpecialtyById(Long id);
	
	void deleteSpecialty(Long id);
}
