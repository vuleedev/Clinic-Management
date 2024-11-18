package com.hamter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.model.AllCode;
import com.hamter.repository.AllCodeRepository;
import com.hamter.service.AllCodeService;

@Service
public class AllCodeService {
	
	@Autowired
    private AllCodeRepository allCodeRepository;

	public List<AllCode> findAll() {
		return allCodeRepository.findAll();
	}

	public AllCode findById(Long id) {
		return allCodeRepository.findById(id).orElse(null);
	}

	public AllCode create(AllCode allCode) {
		return allCodeRepository.save(allCode);
	}

	public AllCode update(AllCode allCode) {
		return allCodeRepository.save(allCode);
	}

	public void delete(Long id) {
		allCodeRepository.deleteById(id);
	}
	
	
}
