package com.hamter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hamter.model.Histories;

public interface HistoryRepository extends JpaRepository<Histories, Long> {

}
