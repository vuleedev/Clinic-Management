package com.hamter.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hamster.interfaceService.IAllcodeService;
import com.hamter.model.Allcodes;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/rest/allCode")
public class AllCodeRestController {
	
	@Autowired
    private IAllcodeService allCodeService;

    @GetMapping
    public List<Allcodes> getAllCodes() {
        return allCodeService.findAll();
    }

    @GetMapping("/{id}")
    public Allcodes findByIdAllCode(@PathVariable Long id) {
        return allCodeService.findById(id);            
    }

    @PostMapping
    public Allcodes createAllCode(@RequestBody Allcodes allCode) {
        return allCodeService.create(allCode);
    }

    @PutMapping("/{id}")
    public Allcodes updateAllCode(@PathVariable Long id, @RequestBody Allcodes allCode) {
        return allCodeService.update(allCode);
    }

    @DeleteMapping("/{id}")
    public void deleteAllCode(@PathVariable Long id) {
        allCodeService.delete(id);   
    }
}
