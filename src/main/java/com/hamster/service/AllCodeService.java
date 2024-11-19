package com.hamster.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamster.interfaceService.IAllcodeService;
import com.hamster.model.Allcodes;
import com.hamster.repository.AllCodeRepository;

@Service
public class AllCodeService implements IAllcodeService {
	
	@Autowired
    private AllCodeRepository allCodeRepository;

	@Override
	public List<Allcodes> findAll() {
		return allCodeRepository.findAll();
	}

	@Override
	public Allcodes findById(Long id) {
		return allCodeRepository.findById(id).orElse(null);
	}

	@Override
	public Allcodes create(Allcodes allCode) {
		return allCodeRepository.save(allCode);
	}

	@Override
	public Allcodes update(Allcodes allCode) {
		return allCodeRepository.save(allCode);
	}

	@Override
	public void delete(Long id) {
		allCodeRepository.deleteById(id);
	}
	
	
}
