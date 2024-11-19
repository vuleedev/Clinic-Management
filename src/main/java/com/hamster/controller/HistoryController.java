package com.hamster.controller;

import java.awt.PageAttributes.MediaType;
import java.net.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hamster.dto.HistoryDTO;
import com.hamster.model.Histories;
import com.hamster.service.HistoryService;

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("/api/histories")
public class HistoryController {
	 private final HistoryService historyService;

	 @Autowired
	 public HistoryController(HistoryService historyService) {
		 this.historyService = historyService;
	}
	 
	 @GetMapping
	    public List<HistoryDTO> getAllHistories() {
	        List<Histories> histories = historyService.getAllHistories();
	        return histories.stream()
	                        .map(historyService::convertToDTO)
	                        .toList();
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<HistoryDTO> getHistoryById(@PathVariable Long id) {
	        Optional<Histories> history = historyService.getHistoryById(id);
	        return history.map(value -> ResponseEntity.ok(historyService.convertToDTO(value)))
	                      .orElseGet(() -> ResponseEntity.notFound().build());
	    }

	    @PostMapping
	    public ResponseEntity<HistoryDTO> createHistory(@RequestBody HistoryDTO historyDTO) {
	        Histories history = historyService.convertToEntity(historyDTO);
	        Histories createdHistory = historyService.createHistory(history);
	        return ResponseEntity.ok(historyService.convertToDTO(createdHistory));
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<HistoryDTO> updateHistory(@PathVariable Long id, @RequestBody HistoryDTO historyDTO) {
	        Histories history = historyService.convertToEntity(historyDTO);
	        Histories updatedHistory = historyService.updateHistory(id, history);
	        return ResponseEntity.ok(historyService.convertToDTO(updatedHistory));
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteHistory(@PathVariable Long id) {
	        historyService.deleteHistory(id);
	        return ResponseEntity.noContent().build();
	    }
	    
	    @GetMapping("/{id}/files")
	    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) throws java.io.IOException {
	        Optional<Histories> historyOptional = historyService.getHistoryById(id);

	        if (!historyOptional.isPresent()) {
	            return ResponseEntity.notFound().build();
	        }

	        Histories history = historyOptional.get();

	        String fileDataBase64 = history.getFiles();  // Lấy chuỗi base64 từ entity

	        if (fileDataBase64 == null || fileDataBase64.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        }

	        byte[] fileData = Base64.getDecoder().decode(fileDataBase64);  // Chuyển chuỗi base64 sang byte[]

	        String mimeType = "application/octet-stream";  // Có thể thay đổi tùy theo loại file

	        try {
	            mimeType = Files.probeContentType(Paths.get("path_to_your_file"));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return ResponseEntity.ok()
	                .contentType(org.springframework.http.MediaType.parseMediaType(mimeType))
	                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"history-file\"")
	                .body(fileData);
	    }
}
