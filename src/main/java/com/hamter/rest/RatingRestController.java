package com.hamter.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hamter.dto.RatingDTO;
import com.hamter.service.RatingService;

@RestController
@RequestMapping("/api/ratings")
public class RatingRestController {
	
    @Autowired
    private RatingService ratingService;

    @PutMapping("/save")
    @PreAuthorize("hasAuthority('CUST')")
    public ResponseEntity<?> saveRatings(@RequestBody List<RatingDTO> ratingRequests) {
        for (RatingDTO request : ratingRequests) {
            ratingService.saveRating(request.getHistoryId(), request.getRatingValue(), request.getComment());
        }
        return ResponseEntity.ok("Đánh giá đã được lưu thành công.");
    }
}
