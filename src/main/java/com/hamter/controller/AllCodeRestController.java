package com.hamter.rest;

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

import com.hamter.model.AllCode;
import com.hamter.service.AllCodeService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/rest/allCode")
public class AllCodeRestController {
	
	@Autowired
    private AllCodeService allCodeService;

    @GetMapping
    public List<AllCode> getAllCodes() {
        return allCodeService.findAll();
    }

    @GetMapping("/{id}")
    public AllCode findByIdAllCode(@PathVariable Long id) {
        return allCodeService.findById(id);            
    }

    @PostMapping
    public AllCode createAllCode(@RequestBody AllCode allCode) {
        return allCodeService.create(allCode);
    }

    @PutMapping("/{id}")
    public AllCode updateAllCode(@PathVariable Long id, @RequestBody AllCode allCode) {
        return allCodeService.update(allCode);
    }

    @DeleteMapping("/{id}")
    public void deleteAllCode(@PathVariable Long id) {
        allCodeService.delete(id);   
    }
}
