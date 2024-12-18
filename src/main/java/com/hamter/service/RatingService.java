package com.hamter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.model.History;
import com.hamter.model.Rating;
import com.hamter.repository.HistoryRepository;
import com.hamter.repository.RatingRepository;

@Service
public class RatingService {
	
    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private RatingRepository ratingRepository;

    public Rating saveRating(Long historyId, Integer ratingValue, String comment) {
        History history = historyRepository.findById(historyId)
            .orElseThrow(() -> new RuntimeException("History not found"));

        Rating rating = new Rating();
        rating.setHistory(history);
        rating.setRatingValue(ratingValue);
        rating.setComment(comment);

        return ratingRepository.save(rating);
    }
}