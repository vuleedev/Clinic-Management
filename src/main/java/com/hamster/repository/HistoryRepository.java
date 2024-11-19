package com.hamster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hamster.model.Histories;

public interface HistoryRepository extends JpaRepository<Histories, Long> {

}
