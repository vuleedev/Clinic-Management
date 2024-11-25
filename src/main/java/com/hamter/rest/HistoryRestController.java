package com.hamter.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hamter.dto.HistoryDTO;
import com.hamter.service.HistoryService;

import java.util.List;

@RestController
@RequestMapping("/api/histories")
public class HistoryRestController {

    @Autowired
    private HistoryService historyService;

    @GetMapping
    public List<HistoryDTO> getAllHistories() {
        return historyService.findAll();
    }

    @GetMapping("/{id}")
    public HistoryDTO getHistoryById(@PathVariable("id") Long id) {
        return historyService.findById(id);
    }

    @PostMapping
    public HistoryDTO createHistory(@RequestBody HistoryDTO history) {
        return historyService.create(history);
    }

    @PutMapping("/{id}")
    public HistoryDTO updateHistory(@PathVariable("id") Long id, @RequestBody HistoryDTO history) {
    	history.setId(id);
        return historyService.update(id, history);
    }

    @DeleteMapping("/{id}")
    public void deleteHistory(@PathVariable("id") Long id) {
        historyService.delete(id);
    }
}
