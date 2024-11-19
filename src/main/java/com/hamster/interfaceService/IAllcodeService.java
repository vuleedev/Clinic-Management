package com.hamster.interfaceService;

import java.util.List;

import com.hamster.model.Allcodes;

public interface IAllcodeService {
	
	List<Allcodes> findAll();
	
	Allcodes findById(Long id);
	
	Allcodes create(Allcodes allCode);
	
	Allcodes update(Allcodes allCode);
	
	void delete(Long id);
}
