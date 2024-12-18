package com.hamter.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hamter.dto.HistoryDTO;
import com.hamter.service.HistoryService;

@RestController
@RequestMapping("/api/histories")
public class HistoryRestController {

    @Autowired
    private HistoryService historyService;
    
    @GetMapping("/all-history/doctor/{doctorId}")
    @PreAuthorize("hasAuthority('STAFF')")
    public List<HistoryDTO> getAllHistoriesByDoctor(@PathVariable Long doctorId) {
        return historyService.findAllHistoriesByDoctorId(doctorId);
    }
    
    @GetMapping("/history/{id}")
    @PreAuthorize("hasAuthority('STAFF')")
    public HistoryDTO getHistoryById(@PathVariable("id") Long id) {
        return historyService.findById(id);
    }
    
    @GetMapping("/history/user/{userId}")
    @PreAuthorize("hasAuthority('STAFF')")
    public HistoryDTO getHistoryByUserId(@PathVariable Long userId) {
        return historyService.findHistoryByUserId(userId);
    }
    
    @PostMapping("/create-history/{userId}")
    @PreAuthorize("hasAuthority('STAFF')")
    public HistoryDTO createHistory(@PathVariable Long userId, @RequestBody HistoryDTO historyDTO) {
        return historyService.create(historyDTO, userId);
    }

    @PutMapping("/history/{id}")
    @PreAuthorize("hasAuthority('STAFF')")
    public HistoryDTO updateHistory(@PathVariable("id") Long id, @RequestBody HistoryDTO history) {
    	history.setId(id);
        return historyService.update(id, history);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('STAFF')")
    public void deleteHistory(@PathVariable("id") Long id) {
        historyService.delete(id);
    }
    
}
