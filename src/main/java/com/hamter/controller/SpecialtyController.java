package com.hamter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hamter.dto.SpecialtyDTO;
import com.hamter.service.SpecialtyService;

@RestController
@RequestMapping("/api/specialties")
public class SpecialtyController {
	@Autowired
    private SpecialtyService specialtyService;

    @GetMapping
    public List<SpecialtyDTO> getAllSpecialties() {
        return specialtyService.getAllSpecialty();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> getSpecialtyById(@PathVariable Long id) {
        return specialtyService.getSpecialtyById(id)
                .map(specialtyDTO -> new ResponseEntity<>(specialtyDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> createSpecialty(@RequestBody SpecialtyDTO specialtyDTO) {
        try {
            SpecialtyDTO createdSpecialty = specialtyService.createSpecialty(specialtyDTO);
            return new ResponseEntity<>(createdSpecialty, HttpStatus.CREATED);
        } catch (Exception e) {
            // Ghi log ngoại lệ nếu cần
            e.printStackTrace();
            // Trả về phản hồi lỗi với mã trạng thái 500
            return new ResponseEntity<>("Lỗi khi tạo Specialty", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> updateSpecialty(@PathVariable Long id, @RequestBody SpecialtyDTO specialtyDTO) {
        SpecialtyDTO updatedSpecialty = specialtyService.updateSpecialty(id, specialtyDTO);
        return updatedSpecialty != null ? new ResponseEntity<>(updatedSpecialty, HttpStatus.OK) 
                                        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialty(@PathVariable Long id) {
        specialtyService.deleteSpecialty(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
