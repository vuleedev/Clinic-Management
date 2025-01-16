package com.hamter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hamter.entity.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
	
}
