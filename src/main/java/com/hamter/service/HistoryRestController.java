package com.hamter.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hamter.model.History;
import com.hamter.service.HistoryService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/rest/histories")
public class HistoryRestController {

    @Autowired
    private HistoryService historyService;

    @GetMapping
    public List<History> getAllHistories() {
        return historyService.findAll();
    }

    @GetMapping("/{id}")
    public History getHistoryById(@PathVariable("id") Long id) {
        return historyService.findById(id);
    }

    @PostMapping
    public History createHistory(@RequestBody History history) {
        return historyService.create(history);
    }

    @PutMapping("/{id}")
    public History updateHistory(@PathVariable("id") Long id, @RequestBody History history) {
    	history.setId(id);
        return historyService.update(history);
    }

    @DeleteMapping("/{id}")
    public void deleteHistory(@PathVariable("id") Long id) {
        historyService.delete(id);
    }
}
