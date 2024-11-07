package com.hamter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.model.AllCode;
import com.hamter.repository.AllCodeRepository;
import com.hamter.service.AllCodeService;

@Service
public class AllCodeServiceImpl implements AllCodeService {
	
	@Autowired
    private AllCodeRepository allCodeRepository;

	@Override
	public List<AllCode> findAll() {
		return allCodeRepository.findAll();
	}

	@Override
	public AllCode findById(Long id) {
		return allCodeRepository.findById(id).orElse(null);
	}

	@Override
	public AllCode create(AllCode allCode) {
		return allCodeRepository.save(allCode);
	}

	@Override
	public AllCode update(AllCode allCode) {
		return allCodeRepository.save(allCode);
	}

	@Override
	public void delete(Long id) {
		allCodeRepository.deleteById(id);
	}
	
	
}
