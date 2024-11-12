package com.hamter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hamter.model.History;
import com.hamter.service.HistoryService;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    // Lấy tất cả lịch sử
    @GetMapping
    public ResponseEntity<List<History>> getAllHistory() {
        List<History> histories = historyService.findAll();
        return new ResponseEntity<>(histories, HttpStatus.OK);
    }

    // Lấy chi tiết lịch sử theo ID
    @GetMapping("/{id}")
    public ResponseEntity<History> getHistoryById(@PathVariable Long id) {
        History history = historyService.findById(id);
        if (history != null) {
            return new ResponseEntity<>(history, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Tạo mới lịch sử
    @PostMapping
    public ResponseEntity<History> createHistory(@RequestBody History history) {
        History createdHistory = historyService.create(history);
        return new ResponseEntity<>(createdHistory, HttpStatus.CREATED);
    }
}
