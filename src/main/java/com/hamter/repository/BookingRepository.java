package com.hamter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hamter.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
