package com.hamter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hamter.model.History;

public interface HistoryRepository extends JpaRepository<History, Long> {

}
